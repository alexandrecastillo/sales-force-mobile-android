package biz.belcorp.salesforce.core.data.repository.uainfo

import biz.belcorp.salesforce.core.data.network.dto.UaResponse
import biz.belcorp.salesforce.core.data.repository.uainfo.cloud.UaInfoCloudStore
import biz.belcorp.salesforce.core.data.repository.uainfo.cloud.dto.UaInfoQuery
import biz.belcorp.salesforce.core.data.repository.uainfo.data.UaInfoDataStore
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.RegionTableMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.SeccionTableMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.UaInfoMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.ZonaTableMapper
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.ua.UARules
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import biz.belcorp.salesforce.core.entities.ua.UaEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isGR

class UaInfoDataRepository(
    private val uaInfoCloudStore: UaInfoCloudStore,
    private val uaDataStore: UaInfoDataStore,
    private val uaInfoMapper: UaInfoMapper,
    private val seccionTableMapper: SeccionTableMapper,
    private val zonaTableMapper: ZonaTableMapper,
    private val regionTableMapper: RegionTableMapper
) : UaInfoRepository {

    override suspend fun sync(country: String, person: Person) {
        val response = uaInfoCloudStore.getUa(country, UaInfoQuery().create(person))
        val parentUa = uaInfoMapper.map(country, person)
        val childrenUas = uaInfoMapper.map(verifyResponse(response, person.role), parentUa)
        uaDataStore.saveChildrenUas(childrenUas)
        saveChildrensInLegacyTables(childrenUas)
        uaDataStore.saveUa(parentUa)
    }

    private fun saveChildrensInLegacyTables(uas: List<UaEntity>) {
        with(onlyAvailableRegions(uas)) {
            filter { x ->
                x.rol in listOf(
                    Rol.SOCIA_EMPRESARIA.codigoRol,
                    Rol.CONSULTORA.codigoRol
                )
            }.let {
                uaDataStore.saveLegacySections(seccionTableMapper.map(it))
            }
            filter { x -> x.rol == Rol.GERENTE_ZONA.codigoRol }.let {
                uaDataStore.saveLegacyZones(zonaTableMapper.map(it))
            }
            filter { x -> x.rol == Rol.GERENTE_REGION.codigoRol }.let {
                uaDataStore.saveLegacyRegions(regionTableMapper.map(it))
            }
        }
    }

    private fun onlyAvailableRegions(uas: List<UaEntity>) =
        uas.filter { x -> x.region !in UARules.UA_REGIONS_EXCLUDED }

    private fun verifyResponse(list: List<UaResponse>, role: Rol): List<UaResponse> {
        return list.takeIf { !role.isGR() } ?: list.firstOrNull()?.zones ?: emptyList()
    }

    override suspend fun getAssociatedUaListByUaKey(
        uaKey: LlaveUA,
        excludeParent: Boolean
    ): List<UaInfo> {
        val uas = uaDataStore.getAssociatedUaListByUaKey(uaKey) ?: return emptyList()
        val parent = uaInfoMapper.map(uas)
        val children = uas.uaChildren
            .map { uaInfoMapper.map(it, isThird = true) }
            .sortedBy { sortedByRolUa(uaKey.roleAssociated, it) }
        return if (excludeParent) children else listOf(parent) + children
    }

    private fun sortedByRolUa(rol: Rol, uaInfo: UaInfo): String {
        return when (rol) {
            Rol.DIRECTOR_VENTAS -> uaInfo.uaKey.codigoRegion.orEmpty()
            Rol.GERENTE_REGION -> uaInfo.uaKey.codigoZona.orEmpty()
            else -> uaInfo.uaKey.codigoSeccion.orEmpty()
        }
    }
}

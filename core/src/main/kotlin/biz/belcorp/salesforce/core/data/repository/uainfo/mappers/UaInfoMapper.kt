package biz.belcorp.salesforce.core.data.repository.uainfo.mappers

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.UNO_NEGATIVO_LONG
import biz.belcorp.salesforce.core.data.network.dto.UaResponse
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.ua.UaEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isSE

class UaInfoMapper {

    fun map(country: String, person: Person): UaEntity {
        return UaEntity(
            code = Constant.NUMERO_UNO.toLong(),
            country = country,
            consultantName = person.firstName,
            region = person.uaKey.codigoRegion.orEmpty(),
            zone = person.uaKey.codigoZona.orEmpty(),
            section = person.uaKey.codigoSeccion.orEmpty(),
            rol = person.role.codigoRol
        )
    }

    fun map(response: List<UaResponse>, parent: UaEntity): List<UaEntity> {
        val uaList: List<UaEntity> = response.map { map(it, parent) }
        val nestedUaList: List<UaEntity> = response.flatMap { mapNestedUA(it, parent) }
        return uaList + nestedUaList
    }

    private fun mapNestedUA(ua: UaResponse, parent: UaEntity): List<UaEntity> {
        return when {
            !ua.zones.isNullOrEmpty() -> {
                val nestedParent = parent.copy(
                    code = "${REGION_PREFIX_CODE}${ua.regionId}".toLong(),
                    consultantName = ua.regionManager,
                    region = ua.regionCode,
                    rol = Rol.GERENTE_REGION.codigoRol,
                    isCovered = ua.covered ?: true
                )
                map(ua.zones, nestedParent)
            }
            !ua.sections.isNullOrEmpty() -> {
                val nestedParent = parent.copy(
                    code = "${ZONE_PREFIX_CODE}${ua.zoneId}".toLong(),
                    consultantName = ua.zoneManager,
                    zone = ua.zoneCode,
                    rol = Rol.GERENTE_ZONA.codigoRol,
                    isCovered = ua.covered ?: true
                )
                map(ua.sections, nestedParent)
            }
            else -> emptyList()
        }
    }

    fun map(response: UaResponse, parent: UaEntity): UaEntity {
        val role = Rol.Builder.construir(parent.rol)
        return UaEntity(
            code = getChildrenCode(response, role),
            id = getChildrenId(response, role),
            description = getChildrenDescription(response, role),
            country = parent.country,
            consultantId = response.consultantId.orEmpty(),
            consultantName = getConsultantName(response).orEmpty(),
            region = response.regionCode ?: parent.region.orEmpty(),
            zone = response.zoneCode ?: parent.zone.orEmpty(),
            section = response.sectionCode ?: parent.section.orEmpty(),
            isCovered = response.covered ?: true,
            rol = role.childRol().codigoRol
        ).apply {
            uaParent.target = parent
        }
    }

    fun map(entity: UaEntity, isThird: Boolean = false): UaInfo = with(entity) {
        return UaInfo(
            code = code,
            country = country.orEmpty(),
            uaKey = LlaveUA(
                region.orEmpty(),
                zone.orEmpty(),
                section.orEmpty(),
                Constant.UNO_NEGATIVO.toLong()
            ),
            role = Rol.Builder.construir(rol),
            isCovered = isCovered,
            isThirdPerson = isThird
        )
    }

    private fun getConsultantName(response: UaResponse): String? {
        return when {
            response.regionManager.orEmpty().isNotEmpty() -> response.regionManager
            response.zoneManager.orEmpty().isNotEmpty() -> response.zoneManager
            response.sectionManager.orEmpty().isNotEmpty() -> response.sectionManager
            else -> EMPTY_STRING
        }
    }

    private fun getChildrenCode(response: UaResponse, role: Rol): Long = with(role) {
        return when {
            isDV() -> "${REGION_PREFIX_CODE}${response.regionId}"
            isGR() -> "${ZONE_PREFIX_CODE}${response.zoneId}"
            isGZ() || isSE() -> "${SECTION_PREFIX_CODE}${response.sectionId}"
            else -> Constant.UNO_NEGATIVO.toString()
        }.toLongOrNull() ?: Constant.UNO_NEGATIVO.toLong()
    }

    private fun getChildrenId(response: UaResponse, role: Rol): Long = with(role) {
        return when {
            isDV() -> response.regionId
            isGR() -> response.zoneId
            isGZ() || isSE() -> response.sectionId
            else -> UNO_NEGATIVO_LONG
        } ?: UNO_NEGATIVO_LONG
    }

    private fun getChildrenDescription(response: UaResponse, role: Rol): String = with(role) {
        return when {
            isDV() -> response.regionDescription
            isGR() -> response.zoneDescription
            isGZ() || isSE() -> response.sectionDescription
            else -> EMPTY_STRING
        } ?: EMPTY_STRING
    }

    companion object {

        private const val REGION_PREFIX_CODE = 30
        private const val ZONE_PREFIX_CODE = 20
        private const val SECTION_PREFIX_CODE = 10

    }

}

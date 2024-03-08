package biz.belcorp.salesforce.modules.digital.core.data.repository.digital

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.DigitalCloudStore
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalParams
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalQuery
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.data.DigitalDataStore
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.mappers.DigitalDataMapper
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalInfo
import biz.belcorp.salesforce.modules.digital.core.domain.repository.DigitalRepository

class DigitalDataRepository(
    private val dataStore: DigitalDataStore,
    private val cloudStore: DigitalCloudStore,
    private val mapper: DigitalDataMapper
) : DigitalRepository {

    override suspend fun sync(country: String, campaign: String, uaKey: LlaveUA) {
        val flags = getFlags(uaKey.roleAssociated)
        val params = DigitalParams(
            country = country,
            campaigns = listOf(campaign),
            profile = uaKey.roleAssociated.codigoRol,
            region = uaKey.codigoRegion?.deleteHyphen().orEmpty(),
            zone = uaKey.codigoZona?.deleteHyphen().orEmpty(),
            section = uaKey.codigoSeccion?.deleteHyphen().orEmpty(),
            showRegions = flags.showRegions,
            showZones = flags.showZones,
            showSection = flags.showSections
        )
        val query = DigitalQuery(params)
        val info = cloudStore.getDigitalInfo(query)
        val entities = mapper.map(info)
        dataStore.saveDigitalInfo(entities)
    }

    override suspend fun getDigitalInfo(campaign: String, uaKey: LlaveUA): DigitalInfo {
        val digitalInfo = dataStore.getDigitalInfo(campaign, uaKey)
        return mapper.map(requireNotNull(digitalInfo))
    }

    override suspend fun getDigitalInfoByParent(campaign: String, uaKey: LlaveUA): List<DigitalInfo> {
        val data = dataStore.getDigitalInfoByParent(campaign, uaKey)
        return data.map { mapper.map(it) }
    }

    private fun getFlags(role: Rol): Flags = with(role) {
        return@with when {
            isDV() -> Flags(showRegions = true, showZones = true, showSections = false)
            isGR() -> Flags(showRegions = false, showZones = true, showSections = true)
            isGZ() -> Flags(showRegions = false, showZones = false, showSections = true)
            else -> Flags()
        }
    }

    private inner class Flags(
        val showRegions: Boolean = false,
        val showZones: Boolean = false,
        val showSections: Boolean = false
    )

}

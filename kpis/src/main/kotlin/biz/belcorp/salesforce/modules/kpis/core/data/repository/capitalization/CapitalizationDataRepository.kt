package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.CapitalizationCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.CapitalizationQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data.CapitalizationDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.CapitalizationMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository

class CapitalizationDataRepository(
    private val capitalizationCloudStore: CapitalizationCloudStore,
    private val capitalizationDataStore: CapitalizationDataStore,
    private val capitalizationMapper: CapitalizationMapper,
    private val queryMapper: KpiQueryMapper
) : CapitalizationRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val query = CapitalizationQuery(queryMapper.mapKpi(request))
        val data = capitalizationCloudStore.getCapitalization(query)
        val entities = capitalizationMapper.map(data)
        capitalizationDataStore.saveCapitalization(entities)
    }

    override suspend fun getKpiDataByCampaignsAndUa(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<CapitalizationIndicator> {
        val data = capitalizationDataStore.getKpiDataByCampaignsAndUa(uaKey, campaigns)
        return data.map { capitalizationMapper.map(it) }
    }

    override suspend fun getConsolidatedByUa(
        uaKey: LlaveUA,
        campaign: String
    ): List<CapitalizationIndicator> {
        val data = capitalizationDataStore.getConsolidatedDataByUa(uaKey, campaign)
        return data.map { capitalizationMapper.map(it) }
    }
}

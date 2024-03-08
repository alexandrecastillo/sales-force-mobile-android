package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.ProfitCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.ProfitQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.ProfitDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.ProfitMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.ProfitIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.ProfitRepository

class ProfitDataRepository(
    private val profitCloudStore: ProfitCloudStore,
    private val profitDataStore: ProfitDataStore,
    private val profitMapper: ProfitMapper,
    private val queryMapper: KpiQueryMapper
) : ProfitRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val query = ProfitQuery(queryMapper.mapKpi(request))
        val data = profitCloudStore.getProfit(query)
        val profits = profitMapper.map(data)
        profitDataStore.saveProfit(profits)
    }

    override suspend fun getProfitByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<ProfitIndicator> {
        val data = profitDataStore.getProfitByCampaigns(uaKey, campaigns)
        return data.map { profitMapper.map(it) }.sortedBy { it.campaign }
    }

}

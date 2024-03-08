package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.NewCycleCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.dto.NewCycleQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.data.NewCycleDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.mapper.NewCycleMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository

class NewCycleDataRepository(
    private val newCycleDataStore: NewCycleDataStore,
    private val newCycleCloudStore: NewCycleCloudStore,
    private val newCycleMapper: NewCycleMapper,
    private val queryMapper: KpiQueryMapper
) : NewCycleRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val query = NewCycleQuery(queryMapper.mapKpi(request))
        val data = newCycleCloudStore.getNewCycleIndicator(query)
        val salesOrdersItems = newCycleMapper.map(data)
        newCycleDataStore.saveNewCycles(salesOrdersItems)
    }

    override suspend fun getNewCycleByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<NewCycleIndicator> {
        val data = newCycleDataStore.getNewCyclesByCampaigns(uaKey, campaigns)
        return data.map { newCycleMapper.map(it) }.sortedBy { it.campaign }
    }

    override suspend fun getNewCycleListByParent(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<NewCycleIndicator> {
        val data = newCycleDataStore.getNewCycleListByParent(uaKey, campaigns)
        return data.map { newCycleMapper.map(it) }.sortedBy { it.campaign }
    }
}

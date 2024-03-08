package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.SaleOrdersCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto.SaleOrdersQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.data.SaleOrdersDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.SaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository

class SaleOrdersDataRepository(
    private val saleOrdersCloudStore: SaleOrdersCloudStore,
    private val saleOrdersDataStore: SaleOrdersDataStore,
    private val saleOrdersMapper: SaleOrdersMapper,
    private val queryMapper: KpiQueryMapper
) : SaleOrdersRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val query = SaleOrdersQuery(queryMapper.mapKpi(request))
        val data = saleOrdersCloudStore.getSalesOrders(query)
        val saleOrders = saleOrdersMapper.map(data)
        saleOrdersDataStore.saveSaleOrders(saleOrders)
    }

    override suspend fun getSalesOrdersByCampaigns(
        uaKey: LlaveUA, campaigns: List<String>
    ): List<SaleOrdersIndicator> {
        val data = saleOrdersDataStore.getSalesOrdersByCampaigns(uaKey, campaigns)
        return data.map { saleOrdersMapper.map(it) }
    }

    override suspend fun getSalesOrdersByParent(
        uaKey: LlaveUA,
        campaign: String
    ): List<SaleOrdersIndicator> {
        val data = saleOrdersDataStore.getSaleOrdersByParent(uaKey, campaign)
        return data.map { saleOrdersMapper.map(it) }
    }
}

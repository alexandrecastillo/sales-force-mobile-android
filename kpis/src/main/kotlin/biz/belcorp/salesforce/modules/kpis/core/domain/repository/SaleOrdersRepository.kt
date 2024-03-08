package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator

interface SaleOrdersRepository {
    suspend fun sync(request: KpiQueryParams)
    suspend fun getSalesOrdersByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<SaleOrdersIndicator>

    suspend fun getSalesOrdersByParent(uaKey: LlaveUA, campaign: String): List<SaleOrdersIndicator>
}

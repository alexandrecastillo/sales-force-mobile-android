package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.order

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.order.OrderIndicatorDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.order.OrderIndicatorMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.OrderIndicatorRDD
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.order.OrderIndicatorRDDRepository

class OrderIndicatorRDDDataRepository(
    private val dataStore: OrderIndicatorDataStore,
    private val mapper: OrderIndicatorMapper
) : OrderIndicatorRDDRepository {

    override suspend fun getOrderIndicator(uaKey: LlaveUA, campaign: String): OrderIndicatorRDD {
        return mapper.parseOrder(requireNotNull(dataStore.getSalesOrdersByCampaign(uaKey, campaign)))
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.order

import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.OrderIndicatorRDD

class OrderIndicatorMapper {
    fun parseOrder(saleOrdersEntityModel: SaleOrdersEntity): OrderIndicatorRDD {
        return OrderIndicatorRDD(
            saleOrdersEntityModel.orders,
            saleOrdersEntityModel.ordersGoal,
            saleOrdersEntityModel.fulfillmentOrderAveragePercentage
        )
    }
}

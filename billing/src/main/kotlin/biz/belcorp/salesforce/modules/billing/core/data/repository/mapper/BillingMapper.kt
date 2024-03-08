package biz.belcorp.salesforce.modules.billing.core.data.repository.mapper

import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.utils.SingleMapper

class BillingMapper : SingleMapper<SaleOrdersEntity, Billing>() {

    override fun map(value: SaleOrdersEntity) = with(value) {
        Billing(
            fulfillmentOrdersPercentage = fulfillmentOrderPercentage,
            fulfillmentCatalogSalesPercentage = fulfillmentCatalogSalesPercentage,
            fulfillmentNetSalesPercentage = fulfillmentNetSalePercentage,
            currentNetSales = netSale,
            currentCatalogSales = catalogSale,
            currentOrders = orders,
            netSalesGoal = netSaleGoal,
            catalogSalesGoal = catalogSaleGoal,
            ordersGoal = ordersGoal
        )
    }

}

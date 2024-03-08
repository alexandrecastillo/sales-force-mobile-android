package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL

data class SaleOrdersIndicator(
    val campaign: String = EMPTY_STRING,
    val region: String = EMPTY_STRING,
    val zone: String = EMPTY_STRING,
    val section: String = EMPTY_STRING,
    val profile: String = EMPTY_STRING,
    val netSale: Double = ZERO_DECIMAL,
    val netSaleGoal: Double = ZERO_DECIMAL,
    val fulfillmentNetSalePercentage: Double = ZERO_DECIMAL,
    val catalogSale: Double = ZERO_DECIMAL,
    val catalogSaleGoal: Double = ZERO_DECIMAL,
    val fulfillmentCatalogSalesPercentage: Double = ZERO_DECIMAL,
    val orders: Int = NUMBER_ZERO,
    val ordersGoal: Int = NUMBER_ZERO,
    val highValueOrders: Int = NUMBER_ZERO,
    val lowValueOrders: Int = NUMBER_ZERO,
    val averageAmount: Double = ZERO_DECIMAL,
    val averageAmountGoal: Double = ZERO_DECIMAL,
    val fulfillmentOrderPercentage: Double = ZERO_DECIMAL,
    val fulfillmentOrderAveragePercentage: Double = ZERO_DECIMAL,
    val activityPercentage: Double = ZERO_DECIMAL,
    val activityGoal: Double = ZERO_DECIMAL,
    val activityPegs: Int = NUMBER_ZERO,
    val fulfillmentActivityPercentage: Double = ZERO_DECIMAL,
    val activesInitials: Int = NUMBER_ZERO,
    val activesFinals: Int = NUMBER_ZERO,
    val activesFinalsLastYear: Int = NUMBER_ZERO,
    val activesRetentionPercentage: Double = ZERO_DECIMAL,
    val fulfillmentRetentionPercentage: Double = ZERO_DECIMAL,
    val activesRetentionGoal: Double = ZERO_DECIMAL,
    var multibrandPercentage: String = EMPTY_STRING,
    var multibrandActives: String = EMPTY_STRING,
    var multibrandNoMultibrandActives: String = EMPTY_STRING,
    var multibrandLastCampaign: String = EMPTY_STRING,
    var multibrandVsLastCampaign: String = EMPTY_STRING,
    var multibrandNumVsLastCampaign: String = EMPTY_STRING,
    var multibrandActivesHighValue: String = EMPTY_STRING,
    var multibrandOrdersHighValue: String = EMPTY_STRING,
    var multibrandHighValueNumVsLastCampaign: String = EMPTY_STRING,
    val ordersRange: List<SaleOrdersOrderRange> = emptyList(),
    val salesRange: List<SaleOrdersSaleRange> = emptyList()
) {

    val pendingNetSaleGoal get() = (netSaleGoal - netSale).toInt()
    val pendingOrderGoal get() = ordersGoal - orders
    val pendingPMNP get() = averageAmountGoal - averageAmount
    val pendingActivity get() = fulfillmentActivityPercentage

    val pendingCatalogSaleGoal get() = catalogSaleGoal - catalogSale
    val indicatorsSuccessful
        get() = pendingOrderGoal < NUMBER_ZERO && pendingNetSaleGoal < NUMBER_ZERO

    val indicatorsReached
        get() = pendingOrderGoal == pendingNetSaleGoal && pendingOrderGoal == NUMBER_ZERO
}

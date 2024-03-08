package biz.belcorp.salesforce.core.entities.saleorders

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class SaleOrdersEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var profile: String = EMPTY_STRING,
    var netSale: Double = ZERO_DECIMAL,
    var netSaleGoal: Double = ZERO_DECIMAL,
    var fulfillmentNetSalePercentage: Double = ZERO_DECIMAL,
    var catalogSale: Double = ZERO_DECIMAL,
    var catalogSaleGoal: Double = ZERO_DECIMAL,
    var fulfillmentCatalogSalesPercentage: Double = ZERO_DECIMAL,
    var orders: Int = NUMBER_ZERO,
    var ordersGoal: Int = NUMBER_ZERO,
    var highValueOrders: Int = NUMBER_ZERO,
    var lowValueOrders: Int = NUMBER_ZERO,
    var averageAmount: Double = ZERO_DECIMAL,
    var averageAmountGoal: Double = ZERO_DECIMAL,
    var fulfillmentOrderPercentage: Double = ZERO_DECIMAL,
    var fulfillmentOrderAveragePercentage: Double = ZERO_DECIMAL,
    var activityPercentage: Double = ZERO_DECIMAL,
    var activityGoal: Double = ZERO_DECIMAL,
    var activityPegs: Int = NUMBER_ZERO,
    var fulfillmentActivityPercentage: Double = ZERO_DECIMAL,
    var activesInitials: Int = NUMBER_ZERO,
    var activesFinals: Int = NUMBER_ZERO,
    var activesFinalsLastYear: Int = NUMBER_ZERO,
    var activesRetentionPercentage: Double = ZERO_DECIMAL,
    var fulfillmentRetentionPercentage: Double = ZERO_DECIMAL,
    var activesRetentionGoal: Double = ZERO_DECIMAL,
    var multibrandPercentage: String = EMPTY_STRING,
    var multibrandActives: String = EMPTY_STRING,
    var multibrandNoMultibrandActives: String = EMPTY_STRING,
    var multibrandLastCampaign: String = EMPTY_STRING,
    var multibrandVsLastCampaign: String = EMPTY_STRING,
    var multibrandNumVsLastCampaign: String = EMPTY_STRING,
    var multibrandActivesHighValue: String = EMPTY_STRING,
    var multibrandOrdersHighValue: String = EMPTY_STRING,
    var multibrandHighValueNumVsLastCampaign: String = EMPTY_STRING,

    @Id var id: Long = 0
) {
    @Backlink(to = "saleOrdersOrdersParent")
    var ordersRange: ToMany<SaleOrdersOrderEntity> = ToMany(this, SaleOrdersEntity_.ordersRange)

    @Backlink(to = "saleOrdersSalesParent")
    var salesRange: ToMany<SaleOrdersSaleEntity> = ToMany(this, SaleOrdersEntity_.salesRange)
}

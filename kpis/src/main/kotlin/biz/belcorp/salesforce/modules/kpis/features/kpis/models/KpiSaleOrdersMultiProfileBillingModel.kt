package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant

class KpiSaleOrdersMultiProfileBillingModel(
    code: Int,
    iconRes: Int,
    iconColor: Int,
    backgroundColor: Int,
    header: String,
    type: Int,
    order: Int,
    isThirdPerson: Boolean,
    val title: String = Constant.EMPTY_STRING,
    val goalOrders: IndicatorGoalBar.Model,
    val goalSale: IndicatorGoalBar.Model
) : KpiModel(code, iconRes, iconColor, backgroundColor, header, type, order, isThirdPerson) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KpiSaleOrdersMultiProfileBillingModel

        if (title != other.title) return false
        if (goalOrders != other.goalOrders) return false
        if (goalSale != other.goalSale) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + goalOrders.hashCode()
        result = 31 * result + goalSale.hashCode()
        return result
    }
}

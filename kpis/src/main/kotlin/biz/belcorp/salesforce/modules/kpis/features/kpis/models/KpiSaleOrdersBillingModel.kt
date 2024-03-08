package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant

class KpiSaleOrdersBillingModel(
    code: Int,
    @DrawableRes iconRes: Int,
    @ColorRes iconColor: Int,
    @ColorRes backgroundColor: Int,
    header: String,
    type: Int,
    order: Int,
    isThirdPerson:Boolean,
    val title: String = Constant.EMPTY_STRING,
    val goalBar: IndicatorGoalBar.Model,
    var subtitleFirst: String = Constant.EMPTY_STRING,
    var descriptionFirst: String = Constant.EMPTY_STRING
) : KpiModel(code, iconRes, iconColor, backgroundColor, header, type, order, isThirdPerson) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KpiSaleOrdersBillingModel

        if (title != other.title) return false
        if (goalBar != other.goalBar) return false
        if (subtitleFirst != other.subtitleFirst) return false
        if (descriptionFirst != other.descriptionFirst) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + goalBar.hashCode()
        result = 31 * result + subtitleFirst.hashCode()
        result = 31 * result + descriptionFirst.hashCode()
        return result
    }
}

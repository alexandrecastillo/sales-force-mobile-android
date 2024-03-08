package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

class KpiNewCyclesModel(
    code: Int,
    @DrawableRes iconRes: Int,
    @ColorRes iconColor: Int,
    @ColorRes backgroundColor: Int,
    header: String,
    type: Int,
    order: Int,
    isThirdPerson: Boolean,
    val title: String,
    val description: String,
    val descriptionSecond: String = EMPTY_STRING
) : KpiModel(code, iconRes, iconColor, backgroundColor, header, type, order, isThirdPerson) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KpiNewCyclesModel

        if (title != other.title) return false
        if (description != other.description) return false
        if (descriptionSecond != other.descriptionSecond) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}

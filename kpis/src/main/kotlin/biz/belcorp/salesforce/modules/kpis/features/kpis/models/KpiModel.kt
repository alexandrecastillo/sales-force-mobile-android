package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.UNO_NEGATIVO
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType

open class KpiModel(
    val code: Int = UNO_NEGATIVO,
    val iconRes: Int = UNO_NEGATIVO,
    val iconColor: Int = UNO_NEGATIVO,
    val backgroundColor: Int = UNO_NEGATIVO,
    val header: String = EMPTY_STRING,
    val type: Int = KpiViewType.NONE,
    val order: Int = UNO_NEGATIVO,
    val isThirdPerson: Boolean = false
) {

    val isValid get() = type != KpiViewType.NONE


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KpiModel

        if (code != other.code) return false
        if (iconRes != other.iconRes) return false
        if (iconColor != other.iconColor) return false
        if (backgroundColor != other.backgroundColor) return false
        if (header != other.header) return false
        if (type != other.type) return false
        if (order != other.order) return false
        if (isThirdPerson != other.isThirdPerson) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + iconRes
        result = 31 * result + iconColor
        result = 31 * result + backgroundColor
        result = 31 * result + header.hashCode()
        result = 31 * result + type
        result = 31 * result + order
        result = 31 * result + isThirdPerson.hashCode()
        return result
    }
}

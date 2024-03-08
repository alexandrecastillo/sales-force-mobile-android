package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

class KpiCapitalizationBillingModel(
    code: Int,
    iconRes: Int,
    iconColor: Int,
    backgroundColor: Int,
    header: String,
    type: Int,
    order: Int,
    isThirdPerson: Boolean,
    var subtitleFirst: String = EMPTY_STRING,
    var descriptionFirst: String = EMPTY_STRING,
    var subtitleSecond: String = EMPTY_STRING,
    var descriptionSecond: String = EMPTY_STRING
) : KpiModel(code, iconRes, iconColor, backgroundColor, header, type, order, isThirdPerson) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KpiCapitalizationBillingModel

        if (subtitleFirst != other.subtitleFirst) return false
        if (descriptionFirst != other.descriptionFirst) return false
        if (subtitleSecond != other.subtitleSecond) return false
        if (descriptionSecond != other.descriptionSecond) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + subtitleFirst.hashCode()
        result = 31 * result + descriptionFirst.hashCode()
        result = 31 * result + subtitleSecond.hashCode()
        result = 31 * result + descriptionSecond.hashCode()
        return result
    }
}

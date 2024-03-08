package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

class KpiCapitalizationSaleModel(
    code: Int,
    iconRes: Int,
    iconColor: Int,
    backgroundColor: Int,
    header: String,
    type: Int,
    order: Int,
    isThirdPerson: Boolean,
    var title: String = EMPTY_STRING,
    var subtitleFirst: String = EMPTY_STRING,
    var descriptionFirst: String = EMPTY_STRING,
    val subtitleSecond: String = EMPTY_STRING,
    val descriptionSecond: String = EMPTY_STRING
) : KpiModel(code, iconRes, iconColor, backgroundColor, header, type, order, isThirdPerson) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KpiCapitalizationSaleModel

        if (title != other.title) return false
        if (subtitleFirst != other.subtitleFirst) return false
        if (descriptionFirst != other.descriptionFirst) return false
        if (subtitleSecond != other.subtitleSecond) return false
        if (descriptionSecond != other.descriptionSecond) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + subtitleFirst.hashCode()
        result = 31 * result + descriptionFirst.hashCode()
        result = 31 * result + subtitleSecond.hashCode()
        result = 31 * result + descriptionSecond.hashCode()
        return result
    }
}

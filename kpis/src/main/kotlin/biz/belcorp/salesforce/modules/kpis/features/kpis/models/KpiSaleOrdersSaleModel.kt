package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.salesforce.core.constants.Constant

class KpiSaleOrdersSaleModel(
    code: Int,
    iconRes: Int,
    iconColor: Int,
    backgroundColor: Int,
    header: String,
    type: Int,
    order: Int,
    isThirdPerson: Boolean,
    val title: String = Constant.EMPTY_STRING,
    var description: String = Constant.EMPTY_STRING
) : KpiModel(code, iconRes, iconColor, backgroundColor, header, type, order, isThirdPerson) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KpiSaleOrdersSaleModel

        if (title != other.title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}

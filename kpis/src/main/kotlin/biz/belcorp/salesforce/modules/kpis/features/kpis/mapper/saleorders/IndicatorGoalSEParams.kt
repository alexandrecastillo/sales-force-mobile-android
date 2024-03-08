package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders

import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator

internal data class IndicatorGoalSEParams(
    val model: SaleOrdersIndicator,
    var currencySymbol: String,
    val isBright: Boolean,
    val isThirdPerson: Boolean,
    val iso: String
)

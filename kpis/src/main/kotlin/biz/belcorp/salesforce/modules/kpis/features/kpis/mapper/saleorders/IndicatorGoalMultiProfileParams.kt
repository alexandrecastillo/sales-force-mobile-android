package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders

import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator

internal data class IndicatorGoalMultiProfileParams(
    val model: SaleOrdersIndicator,
    val currencySymbol: String,
    val useOrders: Boolean,
    val isThirdPerson: Boolean
)

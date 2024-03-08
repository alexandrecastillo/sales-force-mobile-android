package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData

class SaleOrderMultiProfileContainer(
    val saleOrdersIndicators: KpiData<SaleOrdersIndicator>,
    val currencySymbol: String,
    val isBilling: Boolean,
    val isThirdPerson: Boolean = false
)

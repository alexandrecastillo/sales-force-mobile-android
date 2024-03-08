package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class OrdersFilter(
    val withDebt: Boolean = false,
    val lowValueOrder: Boolean = false,
    val lowValueOrderPlus: Boolean = false,
    val highValueOrder: Boolean = false,
    val highValueOrderPlus: Boolean = false
) : Filterable

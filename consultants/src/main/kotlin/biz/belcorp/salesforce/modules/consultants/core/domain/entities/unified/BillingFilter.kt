package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class BillingFilter(
    val hasNotBilledOrders: Boolean = false,
    val hasBilledOrders: Boolean = false
) : Filterable

package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class TypeFilter(
    val hasCashPayment: Boolean = false,
    val hasNotCashPayment: Boolean = false
) : Filterable

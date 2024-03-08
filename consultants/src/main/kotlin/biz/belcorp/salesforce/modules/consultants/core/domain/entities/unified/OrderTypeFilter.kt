package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class OrderTypeFilter(
    val lowValue: Boolean = false,
    val lowPlusValue: Boolean = false,
    val highValue: Boolean = false,
    val highPlusValue: Boolean = false
): Filterable

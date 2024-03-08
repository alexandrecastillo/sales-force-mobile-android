package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class RetentionFilter(
    val pegsWithDebt: Boolean = false,
    val pegsWithoutDebt: Boolean = false
) : Filterable

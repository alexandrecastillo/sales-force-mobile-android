package biz.belcorp.salesforce.core.domain.entities.terms

data class SaveTerms(
    val campaign: String,
    val termCode: String,
    val checked: Boolean,
    val active: Boolean
)

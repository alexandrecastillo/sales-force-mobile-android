package biz.belcorp.salesforce.core.domain.entities.terms

sealed class LinkSE {
    object LinkSEApproved : LinkSE()
    object LinkSEBlocked : LinkSE()
    object LinkSEForApprove : LinkSE()
}

package biz.belcorp.salesforce.base.features.home.cookies

sealed class TermsCookiesViewState {
    class SuccessLink(val url: String) : TermsCookiesViewState()
    class SuccessApproved(val success: Boolean) : TermsCookiesViewState()
    class Failed(val message: String) : TermsCookiesViewState()
}

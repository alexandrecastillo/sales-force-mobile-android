package biz.belcorp.salesforce.modules.termsconditions.features.dialog

sealed class TermsDialogViewState {
    class Success(val success: Boolean) : TermsDialogViewState()
    object SuccessSync : TermsDialogViewState()
    class SuccessLink(val url: String) : TermsDialogViewState()
    class SuccessName(val name: String) : TermsDialogViewState()
    class Failed(val message: String) : TermsDialogViewState()
}

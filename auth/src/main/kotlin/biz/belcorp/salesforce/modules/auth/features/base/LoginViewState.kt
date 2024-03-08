package biz.belcorp.salesforce.modules.auth.features.base

sealed class LoginViewState {
    object LoginSuccess : LoginViewState()
    object SyncSuccess : LoginViewState()
    class LoginError(val message: String) : LoginViewState()
    class LoginAuthError(val message: String) : LoginViewState()
    class ReadyToInstall(val modules: List<String>) : LoginViewState()
    class TermValidated(val isApproved: Boolean) : LoginViewState()
}

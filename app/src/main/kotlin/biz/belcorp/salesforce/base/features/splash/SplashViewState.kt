package biz.belcorp.salesforce.base.features.splash

sealed class SplashViewState {
    object Authenticated : SplashViewState()
    object Unauthenticated : SplashViewState()
    class TermValidated(val isApproved: Boolean) : SplashViewState()
    class Failure(val message: String) : SplashViewState()
    class FromObjectBoxException(val isApproved: Boolean)  : SplashViewState()
}

package biz.belcorp.salesforce.base.features.home

sealed class HomeViewState {
    class Success(val model: HomeModel) : HomeViewState()
    class Failure(val message: String) : HomeViewState()
    object SyncSuccess : HomeViewState()
    class TermValidated(val isApproved: Boolean) : HomeViewState()
}

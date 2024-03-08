package biz.belcorp.salesforce.base.features.main

sealed class MainViewState {
    class Success(val model: MainModel) : MainViewState()
    object LogoutSuccess : MainViewState()
}

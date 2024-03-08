package biz.belcorp.salesforce.base.features.home.user

sealed class UserInfoViewState {
    class Success(val user: UserModel) : UserInfoViewState()
    class Failure(val message: String) : UserInfoViewState()
}

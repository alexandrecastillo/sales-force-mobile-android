package biz.belcorp.salesforce.modules.auth.features.business

import androidx.lifecycle.viewModelScope
import biz.belcorp.mobile.components.login.request.RequestBusiness
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.domain.usecases.features.GetFeaturesUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.sync.groups.LoginSyncGroupManager
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.isColorLight
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewModel
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewState
import kotlinx.coroutines.launch

class LoginBusinessViewModel(
    private val loginUseCase: LoginUseCase,
    featuresUseCase: GetFeaturesUseCase,
    termConditionsUseCase: TermConditionsUseCase,
    syncGroupManager: LoginSyncGroupManager,
    private val appBuild: AppBuild
) : LoginViewModel(loginUseCase, featuresUseCase, termConditionsUseCase, syncGroupManager) {

    fun login(params: RequestBusiness) {
        viewModelScope.launch(handler) {
            io {
                val request = getLoginParams(params)
                loginType = loginUseCase.login(request)
                isLoginSupport = false
                _viewState.postValue(LoginViewState.LoginSuccess)
            }
        }
    }

    private fun getLoginParams(params: RequestBusiness): LoginUseCase.Params {
        return LoginUseCase.Params(
            country = requireNotNull(params.country?.iso),
            user = requireNotNull(params.username),
            password = params.password,
            appId = appBuild.productType.toString()
        )
    }

}

package biz.belcorp.salesforce.modules.auth.features.support

import androidx.lifecycle.viewModelScope
import biz.belcorp.mobile.components.login.request.RequestSupport
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.features.GetFeaturesUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.sync.groups.LoginSyncGroupManager
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewModel
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewState
import kotlinx.coroutines.launch

class LoginSupportViewModel(
    private val loginUseCase: LoginUseCase,
    featuresUseCase: GetFeaturesUseCase,
    termConditionsUseCase: TermConditionsUseCase,
    syncGroupManager: LoginSyncGroupManager,
    private val appBuild: AppBuild
) : LoginViewModel(loginUseCase, featuresUseCase, termConditionsUseCase,syncGroupManager) {

    fun support(params: RequestSupport) {
        viewModelScope.launch(handler) {
            io {
                val request = getSupportParams(params)
                loginType = loginUseCase.login(request)
                isLoginSupport = true
                _viewState.postValue(LoginViewState.LoginSuccess)
            }
        }
    }

    private fun getSupportParams(params: RequestSupport): LoginUseCase.Params {
        return LoginUseCase.Params(
            country = requireNotNull(params.country?.iso),
            ua = LlaveUA(
                params.region,
                params.zone,
                params.section,
                null
            ),
            user = requireNotNull(params.username),
            password = params.password,
            appId = appBuild.productType.toString()
        )
    }

}

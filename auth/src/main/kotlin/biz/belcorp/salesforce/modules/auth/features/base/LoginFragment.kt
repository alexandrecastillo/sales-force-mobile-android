package biz.belcorp.salesforce.modules.auth.features.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.dialogs.alerts.addButton
import biz.belcorp.mobile.components.dialogs.alerts.alertDialog
import biz.belcorp.mobile.components.dialogs.alerts.setIcon
import biz.belcorp.salesforce.base.features.main.HomeActivityArgs
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.di.features.DependenciesManager
import biz.belcorp.salesforce.core.dynamic.InstallState
import biz.belcorp.salesforce.core.dynamic.SplitInstallLiveData
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.modules.auth.R
import biz.belcorp.salesforce.base.R as BaseR


abstract class LoginFragment : BaseFragment() {

    private val dependenciesManager by inject<DependenciesManager>()
    private val splitInstallLiveData by inject<SplitInstallLiveData>()

    protected abstract val loginViewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        loginViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        splitInstallLiveData.observe(viewLifecycleOwner, installStateObserver)
    }

    private val viewStateObserver = Observer<LoginViewState> { state ->
        when (state) {
            LoginViewState.LoginSuccess -> {
                loginViewModel.loadModules()
            }
            is LoginViewState.ReadyToInstall -> {
                downloadModules(state.modules)
            }
            LoginViewState.SyncSuccess -> {
                hideLoading()
                loginViewModel.checkTermApproved()
            }
            is LoginViewState.LoginError -> {
                showAlert(message = state.message)
                hideLoading()
            }
            is LoginViewState.TermValidated -> {
                if (state.isApproved) goToHome()
                else goToTerms()
            }
            is LoginViewState.LoginAuthError -> {
                val title = getString(R.string.login_auth_error)
                showAlert(title = title, message = state.message)
                hideLoading()
            }
        }
    }

    private val installStateObserver = Observer<InstallState> { state ->
        when (state) {
            InstallState.Installed -> {
                initDependencies()
                startSync()
            }
            is InstallState.Retry -> {
                showAlert(message = state.message) {
                    showLoading()
                    loginViewModel.loadModules()
                }
                hideLoading()
            }
            is InstallState.Failed -> {
                showAlert(message = state.message)
                hideLoading()
            }
        }
    }

    private fun downloadModules(modules: List<String>) {
        splitInstallLiveData.install(modules)
    }

    private fun initDependencies() {
        dependenciesManager.loadFeaturesModules()
    }

    private fun startSync() {
        loginViewModel.startSync()
    }

    private fun goToHome() {
        val bundle = HomeActivityArgs(fromLogin = true).toBundle()
        navigateTo(R.id.globalToHomeActivity, bundle)
        activity?.finish()
    }

    private fun goToTerms() {
        navigateTo(R.id.globalToTermActivity)
        activity?.finish()
    }


    abstract fun showLoading()

    abstract fun hideLoading()

    private fun showAlert(title: String? = null, message: String, f: (() -> Unit)? = null) {
        alertDialog(title ?: getString(BaseR.string.title_error), message) {
            setIcon(BaseR.drawable.ic_error_red_32)
            addButton(R.string.alerts_alert_2_button) {
                it.dismiss()
                f?.invoke()
            }
        }?.show()
    }

}

package biz.belcorp.salesforce.base.features.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import biz.belcorp.salesforce.base.App
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.utils.Rootutil
import biz.belcorp.salesforce.core.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun getLayout() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            if (Rootutil().isRooted(act)) {
                //if (false) {
                showRootDeviceDialog(act)
            } else {
                if( ((activity?.application) as App).objectBoxDbRestored  && splashViewModel.isLogged()) {
                    splashViewModel.checkIfObjectBoxFailSchemaAndGetTermsApproved()
                }
                else{
                    splashViewModel.checkSession()
                }
                splashViewModel.viewState.observe(viewLifecycleOwner, observeViewState)
            }
        } ?: run {
            splashViewModel.checkSession()
            splashViewModel.viewState.observe(viewLifecycleOwner, observeViewState)
        }
    }

    private val observeViewState = Observer { state: SplashViewState ->
        when (state) {
            is SplashViewState.Authenticated -> splashViewModel.checkTermApproved()
            is SplashViewState.TermValidated -> termValidated(state.isApproved)
            is SplashViewState.Unauthenticated -> goToLogin()
            is SplashViewState.Failure -> toast(state.message)
            is SplashViewState.FromObjectBoxException -> termValidated(state.isApproved)
        }
    }

    private fun termValidated(approved: Boolean) {
        if (approved) {
            goToHome()
        } else {
            goToTerms()
        }
    }

    private fun goToTerms() {
        findNavController().navigate(R.id.splashToTermActivity)
        activity?.finish()
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.splashToLoginActivity)
        activity?.finish()
    }

    private fun goToHome() {
        findNavController().navigate(R.id.splashToHomeActivity)
        activity?.finish()
    }

    private fun showRootDeviceDialog(act: Context) {
        AlertDialog.Builder(act, R.style.AppTheme_FlatDialog)
            .setTitle("Root check")
            .setMessage("Esta aplicación no puede ser ejecutada en un dispositivo rooteado")
            .setPositiveButton("Aceptar") { _, _ ->
                activity?.finish()
            }.setCancelable(false).create().show()
    }
}

package biz.belcorp.salesforce.modules.auth.features.support

import android.os.Bundle
import android.view.View
import biz.belcorp.mobile.components.dialogs.countries.CountryProvider
import biz.belcorp.mobile.components.login.OnLoginActionListener
import biz.belcorp.mobile.components.login.request.Request
import biz.belcorp.mobile.components.login.request.RequestSupport
import biz.belcorp.salesforce.modules.auth.R
import biz.belcorp.salesforce.modules.auth.features.base.LoginFragment
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewModel
import kotlinx.android.synthetic.main.fragment_support.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginSupportFragment : LoginFragment() {

    private val viewModel by viewModel<LoginSupportViewModel>()

    override val loginViewModel: LoginViewModel
        get() = viewModel

    private val args
        get() = arguments?.let {
            LoginSupportFragmentArgs.fromBundle(
                it
            )
        }

    override fun getLayout() = R.layout.fragment_support

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        loginView?.currentCountry = CountryProvider.countryFromIso(args?.countryIso)

        loginView?.registerOnActionListener(object : OnLoginActionListener {
            override fun onActionSuccess(model: Request) {
                showLoading()
                login(model)
            }
        })
    }

    private fun login(model: Request) {
        val request = model as RequestSupport
        viewModel.support(request)
    }

    override fun showLoading() {
        loginView?.startLoading()
    }

    override fun hideLoading() {
        loginView?.cancelLoading()
    }
}

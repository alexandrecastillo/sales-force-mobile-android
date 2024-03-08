package biz.belcorp.salesforce.modules.auth.features.business

import android.os.Bundle
import android.view.View
import biz.belcorp.mobile.components.login.OnLoginActionListener
import biz.belcorp.mobile.components.login.request.Request
import biz.belcorp.mobile.components.login.request.RequestBusiness
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.core.utils.PackageUtils
import biz.belcorp.salesforce.modules.auth.R
import biz.belcorp.salesforce.modules.auth.features.base.LoginFragment
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewModel
import biz.belcorp.salesforce.modules.auth.features.support.LoginSupportFragmentArgs
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginBusinessFragment : LoginFragment() {

    private val viewModel by viewModel<LoginBusinessViewModel>()

    override val loginViewModel: LoginViewModel
        get() = viewModel

    override fun getLayout() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVersionName()
        setupEvents()
    }

    private fun setVersionName() {
        tvAppVersion?.apply {
            text = String.format(
                getString(R.string.version_app),
                PackageUtils.versionName(context)
            )
        }
    }

    private fun setupEvents() {
        loginView?.registerOnActionListener(object : OnLoginActionListener {
            override fun onActionSuccess(model: Request) {
                showLoading()
                login(model)
            }
        })

        btnSupport?.setOnClickListener {
            goToSupport()
        }
    }

    private fun login(model: Request) {
        val request = model as RequestBusiness
        viewModel.login(request)
    }

    private fun goToSupport() {
        val args = LoginSupportFragmentArgs(countryIso = loginView?.currentCountry?.iso)
        navigateTo(R.id.loginToSupportFragment, args.toBundle())
    }

    override fun showLoading() {
        loginView?.startLoading()
    }

    override fun hideLoading() {
        loginView?.cancelLoading()
    }

}

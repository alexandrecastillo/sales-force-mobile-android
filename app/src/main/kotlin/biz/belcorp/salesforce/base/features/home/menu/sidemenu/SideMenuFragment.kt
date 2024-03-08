package biz.belcorp.salesforce.base.features.home.menu.sidemenu

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.base.features.home.menu.model.MenuOptionModel
import biz.belcorp.salesforce.base.features.home.menu.navigation.MenuNavigation
import biz.belcorp.salesforce.base.features.home.user.UserInfoNavFragment
import biz.belcorp.salesforce.base.utils.AnalyticUtils
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant.MESSAGE_CLIPBOARD
import biz.belcorp.salesforce.core.domain.entities.terms.LinkSE
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.utils.PackageUtils
import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.core.utils.toast
import kotlinx.android.synthetic.main.fragment_side_menu.*
import kotlinx.android.synthetic.main.include_toolbar_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class SideMenuFragment : BaseFragment() {

    private val menuViewModel by viewModel<SideMenuViewModel>()
    private val menuNavigation by lazy { get<MenuNavigation>(findNavController()) }

    private val adapter by lazy {
        SideMenuAdapter().apply {
            onClick = {
                handleMenuOptions(it)
            }
        }
    }

    override fun getLayout() = R.layout.fragment_side_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        ViewCompat.setTranslationZ(view, 1F)
        addUserInfoFragment()
        setupToolbar()
        setupRecyclerView()
        setVersionName()
        observeSyncState()
        observeLinkState()
        getOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screenMenu()
        trackAnalytics(ScreenTag.MENU)
    }

    private val viewStateObserver = Observer { state: SideMenuViewState ->
        when (state) {
            is SideMenuViewState.Populate -> setOptionsMenu(state.options)
            is SideMenuViewState.LogoutSuccess -> logout()
            is SideMenuViewState.TermValidation -> checkLinkSEValidation(state.validation)
            is SideMenuViewState.LinkUneteSuccess -> shareLinkUnete(state.linkUnete)
            is SideMenuViewState.Failed -> toast(state.message)
        }
    }

    private fun checkLinkSEValidation(validation: LinkSE) {
        when (validation) {
            is LinkSE.LinkSEApproved -> menuViewModel.shareLinkUnete()
            is LinkSE.LinkSEForApprove -> goToApproveSETerm()
            is LinkSE.LinkSEBlocked -> goToBlockSEDialog()
        }
    }

    private fun getOptionsMenu() {
        menuViewModel.getOptionsMenu(OptionsIdentifier.MORE)
    }

    private fun handleMenuOptions(option: MenuOptionModel) {
        when (option.code) {
            OptionsIdentifier.MORE_LOGOUT -> menuViewModel.logout()
            OptionsIdentifier.MORE_CHAT -> goToChatBotWebView()
            OptionsIdentifier.MORE_LINK_UNETE -> menuViewModel.checkSELinkStatus()
            else -> menuNavigation.handleMenuOptions(OptionsIdentifier.MORE, option.code)
        }
        option.text?.let {
            AnalyticUtils.sideMenu(it)
        }

    }

    private val userInfoFragment by lazy {
        UserInfoNavFragment.newInstance()
    }

    private fun addUserInfoFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.userInfoContainer, userInfoFragment)
            .commit()
    }

    private fun setupToolbar() {
        toolbarHeader?.setNavigationIcon(R.drawable.ic_backspace)
        toolbarHeader?.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun setupRecyclerView() {
        rvSideMenu?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@SideMenuFragment.adapter
        }
    }

    private fun setVersionName() {
        tvAppVersion?.apply {
            text = String.format(
                getString(R.string.version_app),
                PackageUtils.versionName(this.context)
            )
        }
    }

    private fun setOptionsMenu(options: List<MenuOptionModel>) {
        adapter.setData(options)
    }

    private fun observeSyncState() {
        LiveDataBus.from<SideMenuFragment>(EventSubject.HOME_SYNC)
            .observe(viewLifecycleOwner, syncStatusObserver)
    }

    private val syncStatusObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (it.value as? SyncState) {
                SyncState.Updated -> getOptionsMenu()
            }
        }
    }

    private fun observeLinkState() {
        LiveDataBus.from<SideMenuFragment>(EventSubject.SHARE_LINK_SE)
            .observe(viewLifecycleOwner, syncLinkSEObserver)
    }

    private val syncLinkSEObserver = consumableObserver<Boolean> {
        if (it) menuViewModel.shareLinkUnete()
    }

    private fun goToApproveSETerm() {
        findNavController().navigate(R.id.globalToTermsConditionsDialogFragment)
    }

    private fun goToBlockSEDialog() {
        findNavController().navigate(R.id.globalToBlockSEDialogFragment)
    }


    private fun logout() {
        findNavController().navigate(R.id.globalToLoginActivity)
        activity?.finish()
    }

    private fun goToChatBotWebView() {
        findNavController().navigate(R.id.globalToChatBotActivity)
    }

    private fun shareLinkUnete(linkUnete: String) {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(null, linkUnete)
        clipboard.setPrimaryClip(clip)
        toast(MESSAGE_CLIPBOARD)
    }

}

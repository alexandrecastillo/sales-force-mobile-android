package biz.belcorp.salesforce.base.features.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.base.App
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.home.cookies.TermsCookiesFragment
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutsFragment
import biz.belcorp.salesforce.base.features.home.user.UserInfoFragment
import biz.belcorp.salesforce.base.features.sync.utils.startHomeOnDemandSyncWorker
import biz.belcorp.salesforce.base.utils.AnalyticUtils
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.base.utils.navigateToBilling
import biz.belcorp.salesforce.base.utils.navigateToDevelopmentPath
import biz.belcorp.salesforce.components.commons.UaStateObserver
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.events.EventSubject.*
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.features.uainfo.UaInfoViewState
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.states.UaInfoState
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.messaging.features.list.view.BaseNotificationViewState
import biz.belcorp.salesforce.messaging.features.list.view.NotificationPendingViewState
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingFragmentArgs
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_ua_person.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), SelectorBar.SelectorBarListener {

    private val viewModel by viewModel<HomeViewModel>()
    private val includeManager by inject<IncludeManager>()
    private val stateObserver by inject<UaStateObserver>()

    private val kpisFragment by lazy { includeManager.getInclude(Include.DASHBOARD_KPIS) }

    private val userInfoFragment by lazy { UserInfoFragment.newInstance() }
    private val termsCookiesFragment by lazy { TermsCookiesFragment.newInstance() }
    private val shortcutsFragment by lazy { ShortcutsFragment.newInstance() }

    override fun getLayout() = R.layout.fragment_home

    override fun onResume() {
        super.onResume()
        viewModel.hasPendingNotifications()
        sendAnalytics()
        if ((activity?.application as App).objectBoxDbRestored) {
            context?.startHomeOnDemandSyncWorker()
        }
    }

    private fun sendAnalytics() {
        AnalyticUtils.screenHome()
        trackAnalytics(ScreenTag.HOME)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        addFragments()
    }

    private fun setupToolbarMenu() {
        toolbarHeader?.apply {
            inflateMenu(R.menu.menu_toolbar)
            setOnMenuItemClickListener { onMenuItemClicked(it) }
        }
    }

    private fun setupToolbarInformation(model: HomeModel) {
        toolbarHeader?.apply {
            title = getString(R.string.title_toolbar)
            subtitle = model.periodDescription
            setSubtitleTextColor(getColor(model.color))
        }
    }

    private fun onMenuItemClicked(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.notifications -> {
                navigateTo(R.id.globalToNotificationFeature)
                true
            }
            else -> false
        }
    }

    private fun loadPeriodInformation(model: HomeModel) {
        setupToolbarInformation(model)
    }

    private fun loadUasInformation(model: List<SelectorModel>) {
        if (model.isEmpty()) return
        selectorUa?.apply {
            visible()
            headerModel = model.first().apply { isSelected = true }
            dataSet = model.excludeFirst()
            addOnItemSelectorBarListener(this@HomeFragment)
        }
    }

    private fun setupNavigation(model: HomeModel) = with(model) {
        if (showBillingBanner) setupBillingNavigation(this)
        else setupSaleNavigation()
    }

    private fun setupBillingNavigation(model: HomeModel) {
        bannerSale?.gone()
        bannerBilling?.apply {
            val args = BillingFragmentArgs(rol = model.role, uaKey = model.uaKey).toBundle()
            setSafeOnClickListener {
                navigateToBilling(args)
                AnalyticUtils.bannerAvanceFacturacion()
            }
            visible()
        }
    }

    private fun setupSaleNavigation() {
        bannerBilling?.gone()
        bannerSale?.apply {
            setSafeOnClickListener { navigateToDevelopmentPath() }
            visible()
        }
    }

    private fun setup() {
        setupToolbarMenu()
        setupSelector()
        setupViewModel()
        setupSwipeToRefresh()
        setupSyncObservers()
    }

    private fun setupSelector() {
        selectorUa?.addOnItemSelectorBarListener(this)
    }

    private fun addFragments() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.kpisContainer, kpisFragment, "KPIs")
            .replace(R.id.userInfoContainer, userInfoFragment, "UserInfo")
            .replace(R.id.shortcutsContainer, shortcutsFragment, ShortcutsFragment.TAG)
            .commit()
    }

    private fun setupSwipeToRefresh() {
        swipeToRefresh?.setIndicatorColor(R.color.magenta)
        swipeToRefresh?.setOnSafeRefreshListener {
            context?.startHomeOnDemandSyncWorker()
        }
    }

    private fun showLoading() {
        swipeToRefresh?.isRefreshing = true
    }

    private fun hideLoading() {
        swipeToRefresh?.isRefreshing = false
    }

    private fun setupSyncObservers() {
        observeEventSubject(HOME_SYNC, KPIS_KPIS_SYNC, observer = syncStateObserver)
        observeEventSubject(MESSAGING_SYNC, observer = messagingObserver)
        observeEventSubject(TERM_COOKIES, observer = cookiesObserver)
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, infoViewState)
        viewModel.uaInfoViewState.observe(viewLifecycleOwner, uaViewState)
        viewModel.notificationViewState.observe(viewLifecycleOwner, notificationViewState)
        viewModel.tersmViewState.observe(viewLifecycleOwner, termsViewState)
        loadInformation()
    }

    private fun loadInformation() {
        viewModel.getPeriodInformation()
        viewModel.getUaInformation()
        viewModel.checkTermApproved()
    }

    override fun onSelected(model: Any?) {
        val currentModel = model as UaInfoModel
        if (!currentModel.isThirdPerson) {
            uaPersonInfo?.gone()
        } else {
            uaPersonInfo?.visible()
            tvUserName?.apply {
                text = currentModel.userInformation
                setTextColor(getCompatColor(currentModel.userInformationColor))
            }
            tvPeriod?.apply {
                text = currentModel.periodInformation
                setTextColor(getCompatColor(currentModel.periodColor))
            }
        }
        stateObserver.postValue(
            UaInfoState(
                currentModel.uaKey,
                currentModel.role,
                currentModel.person,
                currentModel.isCovered
            )
        )
        AnalyticUtils.seleccionaTuZona(model.labelText)
    }

    private fun updateNotificationIcon(hasPendingNotification: Boolean) {
        val icon = if (hasPendingNotification) R.drawable.ic_notification_dot
        else R.drawable.ic_notification

        val drawable = ContextCompat.getDrawable(requireContext(), icon)
        val size = toolbarHeader?.menu?.size
        size?.let {
            val menuItem = toolbarHeader?.menu?.getItem(Constant.NUMBER_ZERO)
            menuItem?.apply {
                this.icon = drawable
            }
        }
    }

    private val infoViewState = Observer<HomeViewState> {
        when (it) {
            is HomeViewState.Success -> {
                loadPeriodInformation(it.model)
                setupNavigation(it.model)
            }
        }
    }

    private val termsViewState = Observer<HomeViewState> {
        when (it) {
            is HomeViewState.TermValidated -> showCookiesLabel(it.isApproved)
            else -> Unit
        }
    }

    private fun showCookiesLabel(approved: Boolean) {
        if (!approved) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.cookiesContainer, termsCookiesFragment, TermsCookiesFragment.TAG)
                .commit()
        }
    }

    private val uaViewState = Observer<UaInfoViewState> {
        when (it) {
            is UaInfoViewState.Success -> loadUasInformation(it.uas)
        }
    }

    private val notificationViewState = Observer<BaseNotificationViewState> {
        when (it) {
            is NotificationPendingViewState.Success -> updateNotificationIcon(it.model)
            is NotificationPendingViewState.Failure -> Unit
        }
    }

    private val syncStateObserver = consumableObserver<SyncState> {
        when (it) {
            is SyncState.Updated -> {
                loadInformation()
                hideLoading()
            }
            is SyncState.None -> hideLoading()
            is SyncState.Started -> showLoading()
            is SyncState.Failed -> hideLoading()
        }
    }

    private val messagingObserver = consumableObserver<SyncState.Updated> {
        viewModel.hasPendingNotifications()
    }


    private val cookiesObserver = consumableObserver<Boolean> { isApproved ->
        if (isApproved) cookiesContainer?.gone() else cookiesContainer?.visible()
    }

}

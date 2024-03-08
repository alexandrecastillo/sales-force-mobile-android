package biz.belcorp.salesforce.base.features.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.stateview.StateView
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.deeplinks.DeepLinkHandler
import biz.belcorp.salesforce.base.features.home.menu.bottommenu.BottomMenuFragment
import biz.belcorp.salesforce.base.features.sync.utils.startHomeOnLoginSyncWorker
import biz.belcorp.salesforce.base.features.sync.utils.startHomeSyncWorker
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.core.connectivity.ConnectivityLiveData
import biz.belcorp.salesforce.core.connectivity.ConnectivityState
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.gps.RequestListenerGPS
import biz.belcorp.salesforce.core.gps.SubscriberListenerGPS
import biz.belcorp.salesforce.core.update.UpdateManagerLiveData
import biz.belcorp.salesforce.core.update.UpdateState
import biz.belcorp.salesforce.core.utils.get
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_connectivity_status.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(), SubscriberListenerGPS {

    private val deepLinkHandler by lazy { get<DeepLinkHandler>(findNavController()) }

    private val connectivityLiveData by inject<ConnectivityLiveData>()
    private val updateManagerLiveData by inject<UpdateManagerLiveData>()
    private val viewModel by viewModel<MainViewModel>()

    private val estadoProgressReceiver by lazy { EstadoProgressReceiver() }

    private var listenerGPS: RequestListenerGPS? = null

    override fun getLayout() = R.layout.activity_home

    private val args
        get() = intent.extras?.let {
            HomeActivityArgs.fromBundle(it)
        }

    private val fromLogin get() = args?.fromLogin ?: false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        addFragments()
        registerReceivers()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        manageDeepLinks(intent)
    }

    private fun initialize() {

        connectivityLiveData.observe(this, connectivityStateObserver)
        viewModel.viewState.observe(this, viewStateObserver)

        LiveDataBus.from<HomeActivity>(EventSubject.LOGOUT)
            .observe(this, logoutObserver)

        viewModel.checkUpdate()
        viewModel.initSetup()

        manageDeepLinks(intent)
    }

    private fun initializeModel(model: MainModel) {
        updateManagerLiveData.init(model.updateType ?: return)
        updateManagerLiveData.observe(this, updateStateObserver)
    }

    private fun manageDeepLinks(intent: Intent?) {
        deepLinkHandler.manage(intent?.data)
        intent?.data = null
    }

    private val connectivityStateObserver = Observer { state: ConnectivityState ->
        when (state) {
            ConnectivityState.Online -> showOnlineStatus()
            ConnectivityState.Offline -> showOfflineStatus()
        }
    }

    private val updateStateObserver = Observer { state: UpdateState ->
        when (state) {
            is UpdateState.UpdateAvailable -> startUpdate()
            is UpdateState.Downloaded -> notifyUpdateToUser()
        }
    }

    private val viewStateObserver = Observer { state: MainViewState ->
        when (state) {
            is MainViewState.Success -> initializeModel(state.model)
            is MainViewState.LogoutSuccess -> logout()
        }
    }

    private val logoutObserver = Observer { event: ConsumableEvent ->
        event.runAndConsume {
            viewModel.logout()
        }
    }

    override fun onResume() {
        super.onResume()

        if (!fromLogin) {
            startHomeSyncWorker()
        } else {
            startHomeOnLoginSyncWorker()
            removeLoginKey()
        }
        viewModel.fetchFcmToken()
    }

    private fun removeLoginKey() {
        intent.removeExtra(LOGIN_KEY)
    }

    private val bottomMenuFragment by lazy {
        BottomMenuFragment.newInstance()
    }

    private fun addFragments() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.bottomMenuContainer, bottomMenuFragment)
            .commit()
    }

    private fun showOnlineStatus() {
        tvNetworkStatus?.state = StateView.STATE_ACTIVE
        viewModel.checkUpdate()
    }

    private fun showOfflineStatus() {
        tvNetworkStatus?.state = StateView.STATE_INACTIVE
    }

    private fun startUpdate() {
        updateManagerLiveData.startUpdate(this)
    }

    private fun notifyUpdateToUser() {
        Snackbar
            .make(bottomMenuContainer, R.string.restart_to_update, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_restart) { updateManagerLiveData.completeUpdate() }
            .show()
    }

    private fun logout() {
        findNavController()
            .navigate(R.id.globalToLoginActivity)
        finish()
    }

    private fun findNavController() = findNavController(R.id.homeNavigationFragment)

    override fun subscribeListener(listener: RequestListenerGPS) {
        this.listenerGPS = listener
    }

    override fun unsubscribeListener() {
        listenerGPS = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        updateManagerLiveData.handleResult(requestCode, resultCode)
        listenerGPS?.notifyRequestGPS(requestCode, resultCode, data ?: return)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceivers()
    }

    private fun registerReceivers() {
        val intentFilter = IntentFilter(Constant.BROADCAST_ESTADO_PROGRESS)
        registerReceiver(estadoProgressReceiver, intentFilter)
    }

    private fun unregisterReceivers() {
        unregisterReceiver(estadoProgressReceiver)
    }

    inner class EstadoProgressReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            loadingView.visible(intent.getBooleanExtra(SenderEstadoProgress.ESTADO, false))
        }
    }

    companion object {

        const val LOGIN_KEY = "fromLogin"

    }

}

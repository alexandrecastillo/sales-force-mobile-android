package biz.belcorp.salesforce.messaging.features.list.view

import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.utils.DividerItemDecoration
import biz.belcorp.salesforce.components.utils.TabItem
import biz.belcorp.salesforce.components.utils.createTabs
import biz.belcorp.salesforce.components.utils.updateTabWithBadge
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.EmojiCode
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.messaging.R
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics
import biz.belcorp.salesforce.messaging.features.list.adapter.NotificationAdapter
import biz.belcorp.salesforce.messaging.features.list.model.NotificationContainer
import biz.belcorp.salesforce.messaging.features.list.model.NotificationModel
import biz.belcorp.salesforce.messaging.utils.AnalyticUtils
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_notification.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotificationFragment : BaseDialogFragment() {

    private val notificationManager by inject<NotificationManager>()

    private val notificationTodayAdapter by lazy {
        NotificationAdapter(::onClickNotification)
    }
    private val notificationOtherAdapter by lazy {
        NotificationAdapter(::onClickNotification)
    }

    private val viewModel by viewModel<NotificationViewModel>()

    override fun getLayout() = R.layout.fragment_notification

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearNotifications()
        observeSyncState()
        setup()
    }

    private fun clearNotifications() {
        notificationManager.cancelAll()
    }

    private fun setup() {
        setupToolbar()
        setupTabLayout()
        setupRecyclerToday()
        setupRecyclerOther()
        setUpDefaultText()
        setupViewModel()
    }

    private fun setUpDefaultText() {
        tvEmptyListOthers?.text =
            getString(R.string.notification_empty_list_others, createEmoji(EmojiCode.CONFETI))
        tvEmptyListToday?.text =
            getString(R.string.notification_empty_list_today, createEmoji(EmojiCode.CONFETI))
    }

    private fun setupToolbar() {
        (toolbar as MaterialToolbar).apply {
            title = getString(R.string.title_notification)
            setNavigationIcon(R.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(R.color.white))
            setNavigationOnClickListener { closeDialog() }
        }
    }

    private fun setupTabLayout() {
        tabNotifications?.apply {
            setSelectedTabIndicatorColor(getColor(R.color.magenta))
            createTabs(
                listOf(
                    TabItem(getString(R.string.title_tab_news)),
                    TabItem(getString(R.string.title_tab_join_us)),
                    TabItem(getString(R.string.title_tab_paths))
                )
            )
            onTabSelected {
                val topic = decideTopic(it.position)
                viewModel.getNotifications(topic)
                AnalyticUtils.screen(topic)
            }
        }
    }

    private fun setupRecyclerToday() {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        divider.setDrawable(requireContext().getDrawable(R.drawable.divider_notification))
        recyclerTodayNotification?.apply {
            setHasFixedSize(true)
            addItemDecoration(divider)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationTodayAdapter
        }
    }

    private fun setupRecyclerOther() {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        divider.setDrawable(requireContext().getDrawable(R.drawable.divider_notification))
        divider.setHorizontalOffset(R.dimen.content_inset_normal)
        recyclerOtherNotification?.apply {
            setHasFixedSize(true)
            addItemDecoration(divider)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationOtherAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, notificationViewState)
        viewModel.getNotifications(Topics.NEWS)
        AnalyticUtils.screen(Topics.NEWS)
    }

    private fun onClickNotification(item: NotificationModel) {
        viewModel.updateNotification(item)
    }

    private fun updateInformation(model: NotificationContainer) = with(model) {
        updateBadgeIcon(this)
        updateNotifications(this)
    }

    private fun updateBadgeIcon(model: NotificationContainer) = with(model) {
        if (hasPendingNewsNotifications) tabNotifications?.updateTabWithBadge(NUMBER_ZERO)
        if (hasPendingPostulantsNotifications) tabNotifications?.updateTabWithBadge(NUMBER_ONE)
        if (hasPendingRddNotifications) tabNotifications?.updateTabWithBadge(NUMBER_TWO)
    }

    private fun updateNotifications(model: NotificationContainer) = with(model) {
        updateTodayViews(hasTodayNotifications)
        updateOtherViews(hasOtherNotifications)
        notificationTodayAdapter.submitList(model.todayNotifications)
        notificationOtherAdapter.submitList(model.otherNotifications)
    }

    private fun decideTopic(position: Int): String {
        return when (position) {
            NUMBER_ZERO -> Topics.NEWS
            NUMBER_ONE -> Topics.POSTULANTS
            NUMBER_TWO -> Topics.RDD
            else -> Constant.EMPTY_STRING
        }
    }

    private fun updateTodayViews(hasData: Boolean) {
        recyclerTodayNotification?.visible(hasData)
        tvEmptyListToday?.visible(!hasData)
    }

    private fun updateOtherViews(hasData: Boolean) {
        recyclerOtherNotification?.visible(hasData)
        tvEmptyListOthers?.visible(!hasData)
    }

    private fun doAction(item: NotificationModel) {
        val uri = AppUri.create()
            .withParameters(item.uriParams)
            .build()
        openActivity(uri)
    }

    private fun openActivity(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
        dismiss()
    }

    private val notificationViewState = Observer<BaseNotificationViewState> {
        when (it) {
            is NotificationViewState.Action -> doAction(it.item)
            is NotificationViewState.Success -> updateInformation(it.model)
            is NotificationViewState.Failure -> Unit
        }
    }

    private fun observeSyncState() {
        LiveDataBus.from<NotificationFragment>(EventSubject.MESSAGING_SYNC)
            .observe(viewLifecycleOwner, syncStatusObserver)
    }

    private val syncStatusObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (it.value as? SyncState) {
                SyncState.Updated -> refreshTabInfo()
            }
        }
    }

    private fun refreshTabInfo() {
        val topic = decideTopic(tabNotifications.selectedTabPosition)
        viewModel.getNotifications(topic)
        clearNotifications()
    }

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }
}

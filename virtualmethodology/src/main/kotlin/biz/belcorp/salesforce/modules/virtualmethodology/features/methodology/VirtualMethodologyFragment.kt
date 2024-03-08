package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.base.utils.navigateSafe
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.virtualmethodology.R
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.ListContactsFragmentArgs
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.adapters.VirtualMethodologyAdapter
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.model.GroupsSegmentationModel
import biz.belcorp.salesforce.modules.virtualmethodology.features.sync.utils.startModuleSyncWorker
import biz.belcorp.salesforce.modules.virtualmethodology.features.utils.AnalyticUtils
import biz.belcorp.salesforce.modules.virtualmethodology.features.utils.SharedUtilsImg
import kotlinx.android.synthetic.main.fragment_virtual_methodology.*
import kotlinx.android.synthetic.main.include_toolbar_methodology.*
import biz.belcorp.salesforce.base.R as BaseR

class VirtualMethodologyFragment : BaseFragment(), MethodologyView, ShareListener {

    private val presenter by injectFragment<VirtualMethodologyPresenter>()
    override fun getLayout() = R.layout.fragment_virtual_methodology

    private var groupsSegModel = ArrayList<GroupsSegmentationModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(view, 2F)
        prepareToolbar()
        presenter.listGroupsSeg()
        observeSyncState()
    }

    override fun onResume() {
        super.onResume()
        startSync()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.VIRTUAL_METHODOLOGY)
    }

    private fun prepareToolbar() {
        actionBar(toolbarHeaderMethodology) {
            title = getString(R.string.title_virtual_methodology)
            setBackgroundColor(getColor(R.color.color_purple))
            setNavigationIcon(BaseR.drawable.ic_backspace)
            navigationIcon?.mutate()
            navigationIcon?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }

    private fun showGroups() {
        mRecyclerMedologiaVirtuala?.layoutManager = LinearLayoutManager(context)
        mRecyclerMedologiaVirtuala?.adapter = VirtualMethodologyAdapter(groupsSegModel, this) {
            mRecyclerMedologiaVirtuala?.performClick()
        }
    }

    override fun showGroupsSeg(list: List<GroupsSegmentationModel>) {
        if (!list.isNullOrEmpty()) {
            groupsSegModel.clear()
            groupsSegModel.addAll(list)
            groupsSegModel[0].close = true
            showGroups()
        }
    }

    override fun clickOnFacebook(image: ImageView) {
        SharedUtilsImg(requireActivity()).sharedFacebookPost(image)
    }

    override fun clickOnWhatsApp(image: ImageView) {
        SharedUtilsImg(requireActivity()).sharedWhatsapp(image)
    }

    override fun clickOnSMS(image: String) {
        val args = ListContactsFragmentArgs(imageUrl = image)
        findNavController().navigateSafe(
            BaseR.id.virtualMethodologyFragmentToVirtualMethodologyContactsFragment,
            args.toBundle()
        )
    }

    private fun startSync() {
        context?.startModuleSyncWorker()
    }

    private fun observeSyncState() {
        LiveDataBus.from<VirtualMethodologyFragment>(EventSubject.VIRTUAL_METHODOLOGY_SYNC)
            .observe(viewLifecycleOwner, syncStatusObserver)
    }

    private val syncStatusObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (it.value as? SyncState) {
                SyncState.Updated -> presenter.listGroupsSeg()
            }
        }
    }

}

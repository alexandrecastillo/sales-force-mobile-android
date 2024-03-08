package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.components.di.injectSharedModule
import biz.belcorp.salesforce.components.features.di.SHARED_SCOPE
import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.base.ScopedDialogFragment
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_kpis_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class KpiDetailFragment : ScopedDialogFragment() {

    private val viewModel by viewModel<KpiDetailViewModel>()

    private var fragmentCreator: FragmentCreator<KpiFragmentParameters>? = null

    private val args by lazy { requireNotNull(arguments?.let { KpiDetailFragmentArgs.fromBundle(it) }) }

    override fun getLayout() = R.layout.fragment_kpis_detail

    override fun getScopeName() = SHARED_SCOPE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectSharedModule()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onResume() {
        super.onResume()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screenKPI(args.kpiType)
        activity?.let { AnalyticUtils.trackKPI(args.kpiType, it) }
    }

    private fun showButtonDetail(params: KpiFragmentParameters) {
        val fragment = DetailButtonFragment.newInstance()
        childFragmentManager.beginTransaction().replaceOnce(
            R.id.fcvButtonDetail,
            fragment.javaClass.simpleName,
            bundleOf(FragmentParameters.key to params.clone()),
            childFragmentManager
        ) { fragment }.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) return
        setupViewModels()
        prepareToolbar()
        setupObserver()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getParams(args.kpiType)
    }

    private fun prepareToolbar() {
        actionBar(toolbar as MaterialToolbar) {
            title = args.kpiName
            setNavigationIcon(BaseR.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener { closeDialog() }
        }
    }

    private fun setupObserver() {
        val eventSubject = getEventSubject() ?: return
        LiveDataBus.from<KpiDetailFragment>(eventSubject)
            .observe(viewLifecycleOwner, syncStateObserver)
    }

    private fun getEventSubject(): EventSubject? {
        return when (args.kpiType) {
            KpiType.NEW_CYCLES,
            KpiType.SALE_ORDERS,
            KpiType.CAPITALIZATION -> EventSubject.KPIS_KPIS_SYNC
            KpiType.COLLECTION -> EventSubject.KPIS_COLLECTION_SYNC
            else -> null
        }
    }

    private fun initFragment(params: KpiFragmentParameters) {
        showButtonDetail(params)
        fragmentCreator = KpiCreatorBuilder.build(params)
        fragmentCreator?.withManager(childFragmentManager)?.withRol()?.commit()
    }

    private val viewStateObserver = Observer<KpiDetailViewState> { state ->
        when (state) {
            is KpiDetailViewState.Success -> initFragment(state.kpiDetailParams)
            is KpiDetailViewState.Error ->
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    private val syncStateObserver = Observer<ConsumableEvent> {
        if (!isAttached()) return@Observer
        it.runAndConsume {
            when (it.value) {
                SyncState.Updated -> {
                    fragmentCreator?.clear()
                    viewModel.getParams(args.kpiType)
                }
            }
        }
    }
}

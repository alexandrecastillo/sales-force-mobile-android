package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.se

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.decorations.SpaceDecoration
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.salesforce.components.utils.DividerItemDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.removeDecorations
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.adapter.SegmentFeedListAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.adapter.SegmentProgressListAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.ProgressModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.SegmentFeedModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.NewCycleIndicatorViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header.NewCycleHeaderFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header.NewCycleHeaderViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled.CoupledDetailedKpiAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import kotlinx.android.synthetic.main.fragment_newcycle_detail_se.*
import kotlinx.android.synthetic.main.include_newcycle_tip.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class NewCycleDetailSeFragment : BaseFragment() {

    private val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    private val viewModel by viewModel<NewCycleIndicatorViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    override fun getLayout(): Int = R.layout.fragment_newcycle_detail_se

    private val viewStateObserver = Observer { state: NewCycleHeaderViewState ->
        when (state) {
            is NewCycleHeaderViewState.NewCycleIndicatorListSuccess -> {
                initView(state.kpiNewCycleIndicator)
            }
            is NewCycleHeaderViewState.NewCycleIndicatorError -> {
                Log.e(NewCycleHeaderFragment.TAG, state.message)
            }
        }
    }

    private val viewUaStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.UpdateItem -> updateUa(state.uaKey)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }

    private fun updateUa(uaKey: LlaveUA) {
        params.uaKey = uaKey
        updateDetail(uaKey)
    }

    private fun updateDetail(uaKey: LlaveUA) {
        viewModel.fetchNewCycleIndicator(uaKey)
    }

    private fun initView(newCycleIndicatorModel: NewCycleIndicatorModel) {
        newCycleIndicatorModel.apply {
            setupRecyclerViews(newCycleIndicatorModel)
            tvTitle?.text = title
            if (summary.isNotEmpty()) {
                clTipContainer?.visible()
                tvTipDescription?.text = summary
            }
        }
    }

    private fun setupRecyclerViews(newCycleIndicatorModel: NewCycleIndicatorModel) {
        newCycleIndicatorModel.apply {
            setupSegmentListRecycler(
                segmentFeedList = newCycleIndicatorModel.segmentFeedListModel,
                isGridEnabled = !isBilling
            )
            if (!isBilling) {
                setupRetentionRecyclerView(retentionResultsTitle, retentionResults)
            } else {
                setupBillingRecyclerView(newCycleIndicatorModel.segmentFeedListModel.flatMap { it.list })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        uaViewModel.uaViewState.observe(viewLifecycleOwner, viewUaStateObserver)
        updateDetail(params.uaKey)
    }

    private fun setupBillingRecyclerView(segmentList: List<ProgressModel>) {
        val billingSegmentAdapter = SegmentProgressListAdapter()
        rvBillingSegments?.apply {
            removeDecorations()
            visible()
            adapter = billingSegmentAdapter
            val spacing = resources.getDimensionPixelSize(R.dimen.kpi_content_inset_normal)
            layoutManager = LinearLayoutManager(context)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(view?.getDrawable(BaseR.drawable.divider_newcycle))
            addItemDecoration(itemDecoration)
            addItemDecoration(SpaceDecoration(top = spacing, bottom = spacing))
        }
        billingSegmentAdapter.swap(segmentList.sortedByDescending { it.title })
    }

    private fun setupRetentionRecyclerView(title: String, list: List<CoupledModel>) {
        val profitReceivedAdapter = CoupledDetailedKpiAdapter()
        rvResultItems?.apply {
            visible()
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = profitReceivedAdapter
        }
        tvRetentionResults?.text = title
        groupRetention?.visible()
        profitReceivedAdapter.update(list)
    }

    private fun setupSegmentListRecycler(
        segmentFeedList: List<SegmentFeedModel>,
        isGridEnabled: Boolean
    ) {
        val segmentFeedAdapter = SegmentFeedListAdapter(isGridEnabled = isGridEnabled)
        rvSegmentFeed?.apply {
            removeDecorations()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(view?.getDrawable(BaseR.drawable.divider_newcycle))
            addItemDecoration(itemDecoration)
            adapter = segmentFeedAdapter
        }
        segmentFeedAdapter.swap(segmentFeedList)
    }

    companion object {
        val TAG = NewCycleDetailSeFragment::class.java.simpleName

        fun newInstance(): NewCycleDetailSeFragment = NewCycleDetailSeFragment()
    }
}

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.salesforce.components.utils.DividerItemDecoration
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.adapter.SegmentFeedListAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.SegmentFeedModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.NewCycleIndicatorViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled.CoupledDetailedKpiAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import kotlinx.android.synthetic.main.fragment_new_cycle_header.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class NewCycleHeaderFragment : BaseFragment() {

    private val viewModel by viewModel<NewCycleIndicatorViewModel>()

    private val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters

    override fun getLayout() = R.layout.fragment_new_cycle_header

    private val dividerDecoration by lazy {
        LineDividerDecoration(
            requireContext(),
            BaseR.drawable.divider_newcycle,
            BaseR.dimen.content_inset_less
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.fetchNewCycleIndicator(params.uaKey)
    }

    private val viewStateObserver = Observer { state: NewCycleHeaderViewState ->
        when (state) {
            is NewCycleHeaderViewState.NewCycleIndicatorListSuccess -> initView(state.kpiNewCycleIndicator)
            is NewCycleHeaderViewState.NewCycleIndicatorError -> Log.e(TAG, state.message)
        }
    }

    private fun initView(newCycleIndicatorModel: NewCycleIndicatorModel) {
        newCycleIndicatorModel.apply {
            setupSegmentListRecycler(segmentFeedListModel, newCycleIndicatorModel.hasWidthFitGrid)
            if (!isBilling) { setupRetentionRecyclerView(retentionResultsTitle, retentionResults) }
            tvTitle?.text = title
        }
    }

    private fun setupRetentionRecyclerView(title: String, list: List<CoupledModel>) {
        val profitReceivedAdapter = CoupledDetailedKpiAdapter()
        rvResultItems?.apply {
            visible()
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(dividerDecoration)
            adapter = profitReceivedAdapter
        }
        tvRetentionResults?.text = title
        groupRetention?.visible()
        profitReceivedAdapter.update(list)
    }


    private fun setupSegmentListRecycler(segmentFeedList: List<SegmentFeedModel>, hasWidthFitGrid: Boolean) {
        val segmentFeedAdapter = SegmentFeedListAdapter(hasWidthFitGrid = hasWidthFitGrid)
        rvSegmentFeed?.apply {
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
        val TAG = NewCycleHeaderFragment::class.java.simpleName

        fun newInstance() = NewCycleHeaderFragment()
    }
}

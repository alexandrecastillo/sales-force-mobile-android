package biz.belcorp.salesforce.modules.billing.features.billing.view.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.decorations.DividerLinearDecoration
import biz.belcorp.mobile.components.core.extensions.getDrawableAttr
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingFragmentParameters
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingNewCycleModel
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingRejectedOrderModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewState
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.consultantorders.NewCycleAdapter
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.consultantorders.RejectedOrdersAdapter
import kotlinx.android.synthetic.main.fragment_billing_detail_multiprofile.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class BillingMultiProfileDetailFragment : BaseFragment() {

    private val viewModel by viewModel<BillingMultiProfileDetailViewModel>()
    private val uaViewModel by sharedViewModel<BillingViewModel>(from = { requireParentFragment() })

    private val args by lazy {
        arguments?.getSerializable(FragmentParameters.key) as BillingFragmentParameters
    }

    private val newCycleAdapter by lazy { NewCycleAdapter() }
    private val rejectedOrdersAdapter by lazy { RejectedOrdersAdapter() }

    override fun getLayout() = R.layout.fragment_billing_detail_multiprofile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNewCycleView()
        setupRejectedOrdersView()
        setup()
    }

    private fun setup() {
        viewModel.viewState.observe(viewLifecycleOwner, billingViewStateObserver)
        viewModel.viewStateRejectedOrders.observe(
            viewLifecycleOwner,
            rejectedOrdersViewStateObserver
        )
        setupViewModel()
        listenBillingChanges()
    }

    private fun listenBillingChanges() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewStateObserver)
    }

    private fun setupViewModel() {
        viewModel.getBillingDetailAdvancement(args.uaKey)
        viewModel.getRejectedOrders(args.uaKey)
    }

    private fun setupNewCycleView() {
        setupNewCycleRecyclerView()
    }

    private fun setupNewCycleRecyclerView() {
        recyclerNewCycle?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            adapter = newCycleAdapter
            addItemDecoration(
                DividerLinearDecoration(
                    getDrawableAttr(BaseR.attr.listDivider),
                    showLastDivider = false
                )
            )
        }
    }

    private fun setupRejectedOrdersView() {
        setupRejectedOrdersRecyclerView()
    }

    private fun setupRejectedOrdersRecyclerView() {
        recyclerRejected?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            adapter = rejectedOrdersAdapter
        }
    }

    private fun loadNewCycleData(title: String, model: List<BillingNewCycleModel>) {
        textNewCycleFirstSummary?.text = title
        newCycleAdapter.submitList(model)
    }

    private fun loadPegsData(model: IndicatorGoalBar.Model) {
        indicatorPegs?.update(model)
    }

    private fun loadRejectedOrders(model: List<BillingRejectedOrderModel>) {
        rejectedOrdersAdapter.submitList(model)
        recyclerRejected?.visible()
        tvEmptyRejected?.gone()
    }

    private fun updateUa(uaKey: LlaveUA) {
        args.uaKey = uaKey
        viewModel.getBillingDetailAdvancement(uaKey)
        viewModel.getRejectedOrders(uaKey)
    }

    private fun showRejectedEmptyView() {
        recyclerRejected?.gone()
        tvEmptyRejected?.visible()
    }

    private val uaViewStateObserver = Observer<BillingViewState> { state ->
        when (state) {
            is BillingViewState.UpdateItem -> updateUa(state.uaKey)
        }
    }

    private val billingViewStateObserver = Observer<BillingMultiProfileDetailViewState> {
        when (it) {
            is BillingMultiProfileDetailViewState.Success -> {
                loadNewCycleData(it.model.newCycleTitle, it.model.pendingNewCycle)
                loadPegsData(it.model.pendingPegs)
            }
        }
    }

    private val rejectedOrdersViewStateObserver = Observer<RejectedOrdersViewState> {
        when (it) {
            is RejectedOrdersViewState.Success -> loadRejectedOrders(it.model)
            is RejectedOrdersViewState.EmptyView -> showRejectedEmptyView()
        }
    }

    companion object {
        val TAG = BillingMultiProfileDetailFragment::class.java.simpleName
        fun newInstance(): BillingMultiProfileDetailFragment {
            return BillingMultiProfileDetailFragment()
        }
    }
}

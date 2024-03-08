package biz.belcorp.salesforce.modules.billing.features.billing.view.header.se

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingFragmentParameters
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingHeaderModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewState
import kotlinx.android.synthetic.main.include_billing_header.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BillingHeaderFragment : BaseFragment() {

    private val viewModel by viewModel<BillingHeaderViewModel>()
    private val uaViewModel by sharedViewModel<BillingViewModel>(from = { requireParentFragment() })

    private val args by lazy {
        requireNotNull(arguments?.getSerializable(FragmentParameters.key)) as BillingFragmentParameters
    }

    override fun getLayout() = R.layout.fragment_billing_header

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        viewModel.viewState.observe(viewLifecycleOwner, billingAdvancementObserver)
        if (args.isParent) setupViewModel() else listenBillingChanges()
    }

    private fun setupViewModel() {
        viewModel.getBillingAdvancement(args.uaKey)
    }

    private fun listenBillingChanges() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewStateObserver)
    }

    private fun showBillingHeaderInformation(model: BillingHeaderModel) = with(model) {
        titleInformation()
        tvOrdersDescription?.text = ordersDescription
        tvSalesDescription?.text = salesDescription
    }

    private fun titleInformation() {
        tvSales?.text = getString(R.string.billing_catalog_sale_title)
        tvOrders?.text = getString(R.string.billing_orders_title)
    }

    private fun updateUa(uaKey: LlaveUA) {
        args.uaKey = uaKey
        viewModel.getBillingAdvancement(uaKey)
    }

    private val uaViewStateObserver = Observer<BillingViewState> { state ->
        when (state) {
            is BillingViewState.UpdateItem -> updateUa(state.uaKey)
        }
    }

    private val billingAdvancementObserver = Observer<BillingHeaderViewState> { state ->
        when (state) {
            is BillingHeaderViewState.Success -> showBillingHeaderInformation(state.model)
        }
    }

    companion object {
        val TAG = BillingHeaderFragment::class.java.simpleName

        fun newInstance() = BillingHeaderFragment()
    }
}

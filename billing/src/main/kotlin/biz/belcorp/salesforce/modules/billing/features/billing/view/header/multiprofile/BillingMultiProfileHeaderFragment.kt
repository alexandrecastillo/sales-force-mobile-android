package biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingFragmentParameters
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingHeaderMultiProfileModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewState
import kotlinx.android.synthetic.main.include_billing_header_zone.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BillingMultiProfileHeaderFragment : BaseFragment() {

    private val viewModel by viewModel<BillingMultiProfileHeaderViewModel>()
    private val uaViewModel by sharedViewModel<BillingViewModel>(from = { requireParentFragment() })

    private val args by lazy {
        arguments?.getSerializable(FragmentParameters.key) as BillingFragmentParameters
    }

    override fun getLayout() = R.layout.fragment_billing_header_multi_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        viewModel.viewState.observe(viewLifecycleOwner, billingViewStateObserver)
        setupViewModel()
        listenBillingChange()
    }

    private fun setupViewModel() {
        viewModel.getBillingInformation(args.uaKey)
    }

    private fun listenBillingChange() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewStateObserver)
    }

    private fun bindHeaderInformation(model: BillingHeaderMultiProfileModel) {
        tvOrders?.text = model.titleOrders
        tvOrdersDescription?.text = model.ordersDescription
        tvSales?.text = model.titleSale
        tvSalesDescription?.text = model.salesDescription
    }

    private fun updateUa(uaKey: LlaveUA) {
        args.uaKey = uaKey
        viewModel.getBillingInformation(uaKey)
    }

    private val uaViewStateObserver = Observer<BillingViewState> { state ->
        when (state) {
            is BillingViewState.UpdateItem -> updateUa(state.uaKey)
        }
    }

    private val billingViewStateObserver = Observer<BillingMultiProfileHeaderViewState> {
        when (it) {
            is BillingMultiProfileHeaderViewState.Success -> bindHeaderInformation(it.model)
            is BillingMultiProfileHeaderViewState.Failure -> Unit
        }
    }

    companion object {
        val TAG = BillingMultiProfileHeaderFragment::class.java.simpleName

        fun newInstance(): BillingMultiProfileHeaderFragment {
            return BillingMultiProfileHeaderFragment()
        }
    }
}

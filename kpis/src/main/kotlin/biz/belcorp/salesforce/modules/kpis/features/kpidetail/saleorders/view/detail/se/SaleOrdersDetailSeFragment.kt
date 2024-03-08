package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.concatPercentageSymbol
import biz.belcorp.salesforce.core.utils.ifEmptyOrNullToZero
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail.SaleOrdersContentAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import kotlinx.android.synthetic.main.fragment_sale_orders_detail_se.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class SaleOrdersDetailSeFragment : BaseFragment() {

    private val adapterSaleOrders by lazy { SaleOrdersContentAdapter() }
    private val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    private val viewModel by viewModel<SaleOrdersDetailSeViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    private val linearLayoutManager by lazy {
        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
    private val boxDecoration by lazy {
        BoxOffsetDecoration(
            requireContext(), BaseR.dimen.ds_margin_normal, BaseR.dimen.ds_margin_normal
        )
    }

    override fun getLayout() = R.layout.fragment_sale_orders_detail_se

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels(params.uaKey)
        setupRecycler()
    }

    private fun setupViewModels(uaKey: LlaveUA) {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        uaViewModel.uaViewState.observe(viewLifecycleOwner, viewUaStateObserver)
        updateList(uaKey)
    }

    private val viewStateObserver = Observer<SaleOrdersDetailSeViewState> { state ->
        when (state) {
            is SaleOrdersDetailSeViewState.Success -> {
                tvTitle?.text = state.model.title
                setupData(state.model.items)

            }
            is SaleOrdersDetailSeViewState.Failed -> Unit
        }
    }

    private val viewUaStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.UpdateItem -> updateUa(state.uaKey)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }

    private fun updateList(uaKey: LlaveUA) {
        viewModel.getSaleOrder(uaKey)
    }

    private fun updateUa(ua: LlaveUA) {
        params.uaKey = ua
        updateList(ua)
    }

    private fun setupData(items: List<ContentModel>) = adapterSaleOrders.addData(items)

    private fun setupRecycler() {
        rvResults?.apply {
            setHasFixedSize(true)
            addItemDecoration(boxDecoration)
            layoutManager = linearLayoutManager
            adapter = adapterSaleOrders
        }
    }

    companion object {
        val TAG = SaleOrdersDetailSeFragment::class.java.simpleName

        fun newInstance() = SaleOrdersDetailSeFragment()
    }
}

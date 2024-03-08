package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.components.utils.decoration.ItemOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.removeDecorations
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.CapitalizationKpiOnSalesModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.model.GainDetailSeModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail.SaleOrdersItemAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentBaseModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled.CoupledDetailedKpiAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.KpiGridAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.GridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import kotlinx.android.synthetic.main.fragment_collection_detail_se.*
import kotlinx.android.synthetic.main.include_data_not_available.*
import kotlinx.android.synthetic.main.include_profit_received.*
import kotlinx.android.synthetic.main.include_recovery_advance.*
import kotlinx.android.synthetic.main.view_tip_bottom.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as ComponentR

class GainDetailSeFragment : BaseFragment() {

    private val viewModel by viewModel<GainDetailSeViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    override fun getLayout() = R.layout.fragment_collection_detail_se

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    private val linearLayoutManager
        get() = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

    private val dividerDecoration by lazy {
        LineDividerDecoration(
            requireContext(),
            BaseR.drawable.divider_newcycle,
            ComponentR.dimen.content_inset_less
        )
    }

    private val boxDecoration by lazy {
        BoxOffsetDecoration(
            requireContext(),
            R.dimen.kpi_content_inset_normal,
            R.dimen.kpi_content_inset_normal
        )
    }

    private val itemDecoration
        get() = ItemOffsetDecoration(
            resources.getDimensionPixelOffset(ComponentR.dimen.content_inset_small)
        )

    private lateinit var saleForceStatus : SaleForceStatus
    private lateinit var capitalizationKpiOnSaleModel: CapitalizationKpiOnSalesModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        getSaleForceStatusUpdated()
    }

    fun getSaleForceStatusUpdated(){

        if(UserProperties.session?.rol?.codigoRol == Rol.SOCIA_EMPRESARIA.codigoRol){
            viewModel.saleForceStatusViewState.observe(viewLifecycleOwner,observerSaleForceDataResponse)
            viewModel.loadSaleForceStatus()
        }
        else{
            onSaleForceStatusUpdatedOrFirstLoadAsGZ()
        }
    }

    private val observerSaleForceDataResponse = Observer<SaleForceStatusViewState> { state ->
        when (state) {
            is SaleForceStatusViewState.Success -> {
                saleForceStatus = state.model
                onSaleForceStatusUpdatedOrFirstLoadAsGZ()
            }
            is SaleForceStatusViewState.EmptyView -> toast("Error al obtener el estado de ventas de la campaña anterior")
            is SaleForceStatusViewState.Failed -> toast(state.message)
        }
    }

    private fun onSaleForceStatusUpdatedOrFirstLoadAsGZ(){
        viewModel.capitalizationStatusViewState.observe(viewLifecycleOwner,observerCapitalizationDataResponse)
        viewModel.getCapitalizationData(params.uaKey)
    }

    private fun setupViewModels(uaKey: LlaveUA) {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        uaViewModel.uaViewState.observe(viewLifecycleOwner, viewUaStateObserver)
        updateDetail(uaKey)
    }

    private val observerDataResponse = Observer<GainDetailSeViewState> { state ->
        when (state) {
            is GainDetailSeViewState.Success ->  showData(state.model)
            is GainDetailSeViewState.EmptyView -> showEmptyView(state.uaName)
            is GainDetailSeViewState.Failed -> toast(state.message)
        }
    }

    private val viewUaStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.UpdateItem -> updateUa(state.uaKey)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }

    private val observerCapitalizationDataResponse = Observer<CapitalizationKpiOnSalesModel> { data ->
        capitalizationKpiOnSaleModel = data
        setupViewModels(params.uaKey)
    }

    private fun updateUa(uaKey: LlaveUA) {
        params.uaKey = uaKey
        updateDetail(uaKey)
    }

    private fun updateDetail(uaKey: LlaveUA) {
        if(UserProperties.session?.rol?.codigoRol == Rol.SOCIA_EMPRESARIA.codigoRol){
            viewModel.getGainSe(uaKey, saleForceStatus, capitalizationKpiOnSaleModel)

        }else{
            viewModel.getGainSeFromGz(uaKey)
        }
    }

    private fun setupRecyclerViews() {
        setupRecoveryRecycler()
        setupProfitReceivedRecycler()
        setupChargedRecycler()
    }

    private fun showData(model: GainDetailSeModel) {
        svDetail?.visible()
        iEmptyView?.gone()
        model.apply {
            tvProfitReceivedTitle?.text = profitReceivedTitle
            tvRecoveryAdvanceTitle?.text = recoveryAdvanceTitle
            tvChargedOrderTitle?.text = chargedOrderTitle
            tvDateUpdate?.text = syncDate
            updateRecoveryData(recoveryAdvanceList)
            updateChargedData(chargedOrderList)
            updateProfitReceivedData(profitReceivedList)
            setupTooltipView(tooltip)
        }
    }

    private fun showEmptyView(uaName: String) {
        svDetail?.gone()
        iEmptyView?.visible()
        tvEmptyData?.text = getString(R.string.text_kpi_data_not_available, uaName)
    }

    private fun setupRecoveryRecycler() {
        val retentionAdapter = SaleOrdersItemAdapter()


        rvRecoveryAdvance?.apply {
            setHasFixedSize(true)
            removeDecorations()
            addItemDecoration(boxDecoration)
            isNestedScrollingEnabled = false
            layoutManager = linearLayoutManager
            adapter = retentionAdapter
        }
    }

    private fun updateRecoveryData(list: List<ContentBaseModel>) {
        val adapter = rvRecoveryAdvance.adapter as SaleOrdersItemAdapter
        adapter.addData(list)
    }

    private fun setupTooltipView(tooltip: String) {
        includeNewCycleTip?.visible()
        tvTooltipMessage?.text = tooltip
    }

    private fun setupChargedRecycler() {
        val chargedAdapter = KpiGridAdapter()
        rvChargedOrders?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(),
                Constant.NUMBER_THREE,
                GridLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(itemDecoration)
            addItemDecoration(dividerDecoration)
            adapter = chargedAdapter
        }
    }

    private fun updateChargedData(list: List<GridModel>) {
        val adapter = rvChargedOrders.adapter as KpiGridAdapter
        adapter.update(list)
    }

    private fun setupProfitReceivedRecycler() {
        val profitReceivedAdapter = CoupledDetailedKpiAdapter()
        rvProfitReceived?.apply {
            setHasFixedSize(true)
            addItemDecoration(dividerDecoration)
            layoutManager = linearLayoutManager
            adapter = profitReceivedAdapter
        }
    }

    private fun updateProfitReceivedData(list: List<CoupledModel>) {
        val adapter = rvProfitReceived.adapter as CoupledDetailedKpiAdapter
        adapter.update(list)
    }

    companion object {
        val TAG = GainDetailSeFragment::class.java.simpleName
        fun newInstance() = GainDetailSeFragment().withArguments( )
    }

}

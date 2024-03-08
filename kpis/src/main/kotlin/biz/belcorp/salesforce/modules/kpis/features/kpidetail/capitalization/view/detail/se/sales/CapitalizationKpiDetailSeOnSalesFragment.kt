package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.components.utils.decoration.ItemOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.CapitalizationKpiOnSalesModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.PossiblesPotentialKpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantsKpiFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled.CoupledDetailedKpiAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.KpiGridAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import kotlinx.android.synthetic.main.fragment_sales_on_capitalizatoin_kpi_detail_se.*
import kotlinx.android.synthetic.main.view_card_consultants_quantity.*
import kotlinx.android.synthetic.main.view_tip_bottom.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as ComponentR


class CapitalizationKpiDetailSeOnSalesFragment : BaseFragment() {

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    private val capitalizationDetailedKpiAdapter by lazy { CoupledDetailedKpiAdapter() }
    private val potentialKpiAdapter by lazy { KpiGridAdapter() }
    private val linearLayoutManager by lazy {
        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private val viewModel by viewModel<CapitalizationKpiDetailSeOnSalesViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    private val observerDataResponse =
        Observer<CapitalizationKpiDetailSeOnSalesViewState> { state ->
            when (state) {
                is CapitalizationKpiDetailSeOnSalesViewState.Success ->
                    doOnCapitalizationSuccess(state.data)
                is CapitalizationKpiDetailSeOnSalesViewState.Failed -> doOnFailed()
            }
        }

    private val viewUaStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.UpdateItem -> loadViewData(state.uaKey)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }

    override fun getLayout() = R.layout.fragment_sales_on_capitalizatoin_kpi_detail_se

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPostulantView()
        initRecyclers()
        setupViewModel()
        loadViewData(params.uaKey)
        setupPostulantKpiSection()
    }

    private fun setupPostulantView() {
        childFragmentManager.apply {
            beginTransaction().replaceOnce(
                flDetailBussinesPartnerPostulantKpi.id,
                PostulantsKpiFragment.TAG,
                bundleOf(FragmentParameters.key to params),
                this
            ) {
                PostulantsKpiFragment.newInstance()
            }.commit()
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        if (!params.isParent) uaViewModel.uaViewState.observe(
            viewLifecycleOwner,
            viewUaStateObserver
        )
    }

    private fun setupPostulantKpiSection() {
        if (params.isParent) {
            btnGoToPostulantsKpi?.visible()
            initEvents()
        } else {
            btnGoToPostulantsKpi?.gone()
        }
    }

    private fun initEvents() {
        btnGoToPostulantsKpi?.setOnClickListener { navigateTo(BaseR.id.globalToPostulants) }
    }

    private fun initRecyclers() {
        setupCapitalizationRecycler()
        setupPotentialsRecycler()
    }

    private fun setupCapitalizationRecycler() {
        rvCapitalizationKpiValues?.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = capitalizationDetailedKpiAdapter
        }
    }

    private fun setupPotentialsRecycler() {
        rvPotentialsKpiValues?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(),
                Constant.NUMBER_TWO,
                GridLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                ItemOffsetDecoration(
                    resources.getDimensionPixelOffset(ComponentR.dimen.content_inset_small)
                )
            )
            addItemDecoration(
                LineDividerDecoration(
                    context,
                    BaseR.drawable.divider_newcycle,
                    ComponentR.dimen.content_inset_small
                )
            )
            adapter = potentialKpiAdapter

        }
    }

    private fun loadViewData(ua: LlaveUA) {
        viewModel.getCapitalizationData(ua)
    }

    private fun doOnPossiblesKpiData(model: PossiblesPotentialKpiModel) = with(model) {
        tvPossiblePotentialLabel?.text = totalPotentialKpiLabel
        potentialKpiAdapter.update(potentialKpi)
    }

    private fun doOnCapitalizationSuccess(model: CapitalizationKpiOnSalesModel) = with(model) {
        updateCampaignData(resultsLabel)
        doOnPossiblesKpiData(potentialKpiModel)
        updatePegsData(retentionPercentage)
        updateCapitalizationData(capitalizationKpi)
        setupTooltipView(tooltip)
    }

    private fun updateCampaignData(resultsInCampaignText: String) {
        tvResultsLabel?.text = resultsInCampaignText
    }

    private fun setupTooltipView(toolTipText: String) {
        includedTipBottomView?.apply {
            tvTooltipMessage?.text = toolTipText
        }
    }

    private fun updatePegsData(retentionPercentage: String) {
        tvPegsValue?.text = retentionPercentage
    }

    private fun updateCapitalizationData(model: List<CoupledModel.GridWithHeaderItemModel>) {
        capitalizationDetailedKpiAdapter.update(model)
    }

    private fun doOnFailed() = Unit


    companion object {
        val TAG = CapitalizationKpiDetailSeOnSalesFragment::javaClass.javaClass.simpleName

        fun newInstance() = CapitalizationKpiDetailSeOnSalesFragment()
    }

}

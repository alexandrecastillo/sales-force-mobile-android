package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.components.commons.UaKeyObserver
import biz.belcorp.salesforce.components.features.di.SHARED_CAPITALIZATION_FILTER
import biz.belcorp.salesforce.components.features.di.SHARED_SCOPE
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.utils.injectScoped
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled.CoupledDetailedKpiAdapter
import kotlinx.android.synthetic.main.fragment_capitalization_kpi_on_billing.*
import kotlinx.android.synthetic.main.view_tip_bottom.*
import org.koin.android.viewmodel.ext.android.viewModel

class CapitalizationKpiOnBillingFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_capitalization_kpi_on_billing

    private val viewModel by viewModel<CapitalizationKpiOnBillingViewModel>()

    private val coupledDetailedKpiAdapter by lazy { CoupledDetailedKpiAdapter() }

    private val stateObserver by injectScoped<UaKeyObserver>(
        SHARED_SCOPE,
        SHARED_CAPITALIZATION_FILTER
    )

    private val observerDataResponse =
        Observer<CapitalizationKpiOnBillingViewState> { state ->
            when (state) {
                is CapitalizationKpiOnBillingViewState.Success -> doOnSuccess(state.data)
                is CapitalizationKpiOnBillingViewState.Failed -> doOnFailed()
                is CapitalizationKpiOnBillingViewState.showProactive -> setupTooltipView(state.flagShowProactive, state.userCountryCode)
            }
        }

    private val observerUaDemanded = Observer<ConsumableEvent> {
        it.runAndConsume {
            loadData((it.value as LlaveUA))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclers()
    }

    private fun loadData(ua: LlaveUA) {
        viewModel.getCapitalizationData(ua)
    }

    private fun setupObservers() {
        viewLifecycleOwner.let {
            if (!stateObserver.ua.hasObservers()) stateObserver.ua.observe(it, observerUaDemanded)
            viewModel.viewState.observe(it, observerDataResponse)
        }
    }

    private fun setupRecyclers() {
        rvCapitalizationKpiOnBilling?.apply {
            setHasFixedSize(true)

            addItemDecoration(
                LineDividerDecoration(
                    context,
                    biz.belcorp.salesforce.base.R.drawable.divider_newcycle,
                    biz.belcorp.salesforce.components.R.dimen.content_inset_less
                )
            )

            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = coupledDetailedKpiAdapter
        }
    }

    private fun doOnSuccess(data: List<CoupledModel>) {
        coupledDetailedKpiAdapter.update(data)
        viewModel.getFlagShowProactive()
    }

    private fun doOnFailed() = Unit

    private fun setupTooltipView(flagShowProactive: Boolean, userCountryCode: String) {
        val toolTipTextResourceId = when {
            flagShowProactive && userCountryCode == "PE" -> R.string.text_tip_capitalization_tip_billing_period_capi_peru
            flagShowProactive -> R.string.text_tip_capitalization_tip_billing_period_capi
            else -> R.string.text_tip_capitalization_tip_billing_period
        }

        tvTooltipMessage?.text = getString(toolTipTextResourceId)
    }

    companion object {
        val TAG = CapitalizationKpiOnBillingFragment::javaClass.javaClass.simpleName

        fun newInstance() = CapitalizationKpiOnBillingFragment()
    }

}

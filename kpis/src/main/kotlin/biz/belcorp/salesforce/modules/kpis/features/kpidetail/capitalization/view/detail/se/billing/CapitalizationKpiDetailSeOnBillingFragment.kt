package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.billing

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import biz.belcorp.salesforce.components.commons.UaKeyObserver
import biz.belcorp.salesforce.components.features.di.SHARED_CAPITALIZATION_FILTER
import biz.belcorp.salesforce.components.features.di.SHARED_SCOPE
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.utils.injectScoped
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization.CapitalizationKpiOnBillingFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization.CapitalizationKpiOnBillingViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import kotlinx.android.synthetic.main.fragment_billing_on_capitalizatoin_kpi_detail_se.*
import org.koin.android.viewmodel.ext.android.viewModel

class CapitalizationKpiDetailSeOnBillingFragment : BaseFragment() {

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    private val viewModel by viewModel<CapitalizationKpiOnBillingViewModel>()

    override fun getLayout() = R.layout.fragment_billing_on_capitalizatoin_kpi_detail_se

    private val stateObserver by injectScoped<UaKeyObserver>(
        SHARED_SCOPE,
        SHARED_CAPITALIZATION_FILTER
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCapitalizationKpiOnBillingView()
        loadData()
    }

    private fun loadData() {
        stateObserver.postValue(params.uaKey)
    }

    private fun setupCapitalizationKpiOnBillingView() {
        childFragmentManager.apply {
            beginTransaction().replaceOnce(
                flHeaderCapitalizationKpiOnBilling.id,
                CapitalizationKpiOnBillingFragment().javaClass.simpleName,
                bundleOf(FragmentParameters.key to params),
                this
            ) {
                CapitalizationKpiOnBillingFragment.newInstance()
            }.commit()
        }
    }


    companion object {
        val TAG = CapitalizationKpiDetailSeOnBillingFragment::javaClass.javaClass.simpleName

        fun newInstance() = CapitalizationKpiDetailSeOnBillingFragment()
    }

}

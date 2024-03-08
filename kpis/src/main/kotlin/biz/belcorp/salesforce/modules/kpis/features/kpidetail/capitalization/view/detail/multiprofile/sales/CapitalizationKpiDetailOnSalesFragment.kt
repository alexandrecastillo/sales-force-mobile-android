package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.billing.ConsolidatedCapitalizationKpiFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.pager.PagerKpiDetailFragment

class CapitalizationKpiDetailOnSalesFragment : PagerKpiDetailFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val fragments: List<Fragment> = listOf(
            ConsolidatedCapitalizationKpiFragment.newInstance(params),
            CapitalizationKpiDetailUaFragment.newInstance(params)
        )
        setupPages(fragments)
    }

    companion object {
        val TAG = CapitalizationKpiDetailOnSalesFragment::class.java.simpleName

        fun newInstance(params: KpiFragmentParameters) = CapitalizationKpiDetailOnSalesFragment()
            .withArguments(FragmentParameters.key to params)
    }

}

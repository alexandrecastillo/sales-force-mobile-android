package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.NewCycleGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.uadetail.NewCycleUaDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.pager.PagerKpiDetailFragment

class NewCycleDetailFragment : PagerKpiDetailFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val fragments: List<Fragment> = listOf(
            NewCycleGridSelectorFragment.newInstance(params),
            NewCycleUaDetailFragment.newInstance(params)
        )
        setupPages(fragments)
    }

    companion object {
        val TAG = NewCycleDetailFragment::class.java.simpleName

        fun newInstance(): NewCycleDetailFragment = NewCycleDetailFragment()
    }
}

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.KpiDetailPagerAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_kpi_detail_content.*

abstract class PagerKpiDetailFragment : BaseFragment(), TabLayout.OnTabSelectedListener {

    companion object {
        private const val PAGER_OFFSET = 2
    }

    private val adapter by lazy {
        KpiDetailPagerAdapter(requireContext(), params.rol, requireFragmentManager())
    }

    protected val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters

    override fun getLayout(): Int = R.layout.fragment_kpi_detail_content

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setupTabLayout()
        setupViewPager()
        params.apply { tvTitle?.text = getTitle(rol) }
    }

    private fun getTitle(role: Rol): String {
        return when {
            role.isDV() -> getString(R.string.title_detail_by_region)
            role.isGR() -> getString(R.string.title_detail_by_zone)
            role.isGZ() -> getString(R.string.title_detail_by_section)
            else -> Constant.EMPTY_STRING
        }
    }

    private fun setupViewPager() {
        vpKpiDetail?.offscreenPageLimit = PAGER_OFFSET
        vpKpiDetail?.adapter = adapter
    }

    protected fun setupPages(fragments: List<Fragment>) {
        adapter.update(fragments)
    }

    private fun setupTabLayout() {
        tlKpiDetail?.setupWithViewPager(vpKpiDetail)
        tlKpiDetail?.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        vpKpiDetail?.currentItem = tab.position
        AnalyticUtils.tabSelection(this.tag.toString(), tab.text.toString())
    }

    override fun onTabReselected(tab: TabLayout.Tab) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab) = Unit
}

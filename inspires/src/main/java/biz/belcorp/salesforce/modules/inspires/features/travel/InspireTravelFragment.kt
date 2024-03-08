package biz.belcorp.salesforce.modules.inspires.features.travel

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_TRAVEL
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.utils.onPageSelected
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.Inspira
import biz.belcorp.salesforce.modules.inspires.features.dashboard.InspireDashboardFragment
import biz.belcorp.salesforce.modules.inspires.features.analitycs.AnalyticsInspireViewModel
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.limited.InspireTravelCardLimitedFragment
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.lost.InspireTravelCardLostFragment
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.ranking.InspireTravelCardRankingFragment
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_inspire_travel.*
import kotlinx.android.synthetic.main.fragment_inspire_header.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class InspireTravelFragment : BaseFragment(), InspireTravelView {

    private val presenter: InspireTravelPresenter by inject()
    private val analyticsInspireViewModel by viewModel<AnalyticsInspireViewModel>()
    private lateinit var iBackDashboard: InspireDashboardFragment.IBackDashboardViajeInspira

    override fun getLayout(): Int = R.layout.fragment_inspire_travel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepare()
        setListerns()
    }

    private fun setListerns() {
        iconToBack?.setOnClickListener {
            iBackDashboard.onBackDashboardInspira()
            activity?.onBackPressed()
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showHeaderCard(active: Boolean, limited: Boolean) {
        when {
            active and limited.not() -> loadFragment(InspireTravelCardRankingFragment.newInstance())
            active and limited -> loadFragment(InspireTravelCardLimitedFragment.newInstance())
            active.not() -> loadFragment(InspireTravelCardLostFragment.newInstance())
        }
    }

    override fun createTabs(active: Boolean, limited: Boolean) {
        val adapter = TabsPagerAdapter(requireContext(), limited, active, childFragmentManager)

        tabInspireTravel.setTabsFromPagerAdapter(adapter)

        tabInspireTravel.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                /* Empty */
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                /* Empty */
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { pos ->
                    sendEventsFirebase(limited, active, pos)

                    childFragmentManager.beginTransaction()
                        .replace(R.id.tabContainer, adapter.getItem(pos))
                        .commit()
                }
            }
        })

        if (adapter.count > Constant.NUMBER_ZERO) tabInspireTravel.selectTab(tabInspireTravel.getTabAt(0))
    }

    private fun loadFragment(frag: Fragment) {
        val fm = childFragmentManager.beginTransaction()
        fm.replace(R.id.flyInspireTravelCardContainer, frag)
        fm.commit()
    }

    private fun sendEventsFirebase(limited: Boolean, active: Boolean, position: Int) {
        if (active and limited.not()) {
            when (position) {
                TAB_POSITION_ZERO ->
                    analyticsInspireViewModel.sendInspireEvent(
                        TagAnalytics.EVENTO_INSPIRA_TAB,
                        Inspira(getString(R.string.viaje_inspira_label_ranking), INSPIRA_TRAVEL)
                    )
                TAB_POSITION_ONE ->
                    analyticsInspireViewModel.sendInspireEvent(
                        TagAnalytics.EVENTO_INSPIRA_TAB,
                        Inspira(getString(R.string.viaje_inspira_label_avance), INSPIRA_TRAVEL)
                    )
                TAB_POSITION_TWO ->
                    analyticsInspireViewModel.sendInspireEvent(
                        TagAnalytics.EVENTO_INSPIRA_TAB,
                        Inspira(getString(R.string.viaje_inspira_label_condiciones), INSPIRA_TRAVEL)
                    )
            }
        } else {
            when (position) {
                TAB_POSITION_ZERO ->
                    analyticsInspireViewModel.sendInspireEvent(
                        TagAnalytics.EVENTO_INSPIRA_TAB,
                        Inspira(
                            getString(if (active) R.string.viaje_inspira_label_avance else R.string.viaje_inspira_label_resumen),
                            INSPIRA_TRAVEL
                        )
                    )
                TAB_POSITION_ONE ->
                    analyticsInspireViewModel.sendInspireEvent(
                        TagAnalytics.EVENTO_INSPIRA_TAB,
                        Inspira(getString(R.string.viaje_inspira_label_condiciones), INSPIRA_TRAVEL)
                    )
            }
        }
    }

    fun setIBackDashboardViajeInspira(iBackDashboardParam: InspireDashboardFragment.IBackDashboardViajeInspira) {
        iBackDashboard = iBackDashboardParam
    }

    companion object {

        const val TAB_POSITION_ZERO = 0
        const val TAB_POSITION_ONE = 1
        const val TAB_POSITION_TWO = 2

        const val KEY_ACTIVE = "KEY_ACTIVE"

        @JvmStatic
        fun newInstance(): InspireTravelFragment = InspireTravelFragment()
    }
}

package biz.belcorp.salesforce.modules.calculator.feature.calculator.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.Navigator
import biz.belcorp.salesforce.modules.calculator.feature.calculator.adapter.TabsPagerAdapter
import biz.belcorp.salesforce.modules.calculator.feature.calculator.header.CalculatorCabeceraFragment
import kotlinx.android.synthetic.main.fragment_calculator_dashboard.flResult
import kotlinx.android.synthetic.main.fragment_calculator_dashboard.pager
import kotlinx.android.synthetic.main.fragment_calculator_dashboard.tabs

class CalculatorDashboardFragment : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
    }

    private var navigator: Navigator? = null


    override fun getLayout(): Int = R.layout.fragment_calculator_dashboard

    private fun incrustarFragments() {
        val cabecera =
            CalculatorCabeceraFragment.newInstance(arguments?.getString(Constant.SECTION_PARTNER))
        cabecera.navigator = navigator

        childFragmentManager
            .beginTransaction()
            .replace(R.id.flHeader, cabecera)
            .commit()

        showNewBonus()
    }

    private fun configurarTabs() {
        if (context == null)
            return

        val adapter = TabsPagerAdapter(
            context = requireContext(),
            fm = childFragmentManager,
            partnerSection = arguments?.getString(Constant.SECTION_PARTNER)
        )
        adapter.navigator = navigator
        pager.adapter = adapter
//        pager.onPageSelected { sendEventsFirebase(it) }
        tabs.setupWithViewPager(pager)
    }

    //    private fun sendEventsFirebase(position: Int) {
//        when (position) {
//            Constant.NUMERO_CERO -> analyticsCalculatorViewModel.sendCalculatorEvent(TagAnalytics.EVENTO_CALCULADORA_NIVEL_ACTUAL)
//            Constant.NUMBER_ONE -> analyticsCalculatorViewModel.sendCalculatorEvent(TagAnalytics.EVENTO_CALCULADORA_OTRO_NIVEL)
//        }
//    }
//
    private fun showNewBonus() {
        flResult.visibility = View.GONE
        pager.visibility = View.VISIBLE
        tabs.visibility = View.VISIBLE
        configurarTabs()
    }

    companion object {

        fun newInstance(partnerSection: String?): CalculatorDashboardFragment {
            val fragment = CalculatorDashboardFragment()
            val bundle = bundleOf(Constant.SECTION_PARTNER to partnerSection)
            fragment.arguments = bundle
            return fragment
        }
    }
}

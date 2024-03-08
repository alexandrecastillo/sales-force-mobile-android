package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.view.DashboardRddSe
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view.FocosSEFragment
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator

class TabsSePagerAdapter(private val planId: Long,
                         private val context: Context,
                         fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    var navigator: Navigator? = null

    private val dashboardRddSe: DashboardRddSe by lazy {
        val fragment = DashboardRddSe.newInstance(planId)
        fragment.navigator = navigator
        fragment
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Constant.NUMBER_ZERO -> dashboardRddSe
            Constant.NUMBER_ONE -> FocosSEFragment.newInstance()
            else -> throw IllegalArgumentException("Posición de tab inválido. " +
                    "Revisa el método getCount()")
        }
    }

    override fun getCount(): Int {
        return Constant.NUMBER_TWO
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Constant.NUMBER_ZERO -> context.getString(R.string.rdd_dashboard_tab_mi_seccion)
            Constant.NUMBER_ONE -> context.getString(R.string.rdd_dashboard_tab_focos)
            else -> null
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.DashboardRddDv
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.FocosContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator

class TabsDvPagerAdapter(
    private val planId: Long,
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    var navigator: Navigator? = null

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Constant.NUMBER_ZERO -> DashboardRddDv
                .newInstance(planId)
                .also { it.navigator = navigator }

            Constant.NUMBER_ONE -> FocosContenedorFragment()

            else -> throw IllegalArgumentException(
                "Posición de tab inválido. " +
                    "Revisa el método getCount()"
            )
        }
    }

    override fun getCount(): Int {
        return Constant.NUMBER_TWO
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Constant.NUMBER_ZERO -> context.getString(R.string.dashboard_dv_tab_avance)
            Constant.NUMBER_ONE -> context.getString(R.string.dashboard_gr_tab_focos)
            else -> null
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view.DashboardRddGr
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.FocosContenedorFragment

class TabsGrPagerAdapter(private val planId: Long,
                         private val context: Context,
                         private val esDuenia: Boolean,
                         fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Constant.NUMBER_ZERO -> DashboardRddGr.newInstance(planId)
            Constant.NUMBER_ONE -> FocosContenedorFragment()
            else -> throw IllegalArgumentException("Posición de tab inválido. " +
                    "Revisa el método getCount()")
        }
    }

    override fun getCount(): Int {
        return if (esDuenia) Constant.NUMBER_TWO else Constant.NUMBER_ONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Constant.NUMBER_ZERO -> context.getString(R.string.dashboard_gr_tab_avance)
            Constant.NUMBER_ONE -> context.getString(R.string.dashboard_gr_tab_focos)
            else -> null
        }
    }
}

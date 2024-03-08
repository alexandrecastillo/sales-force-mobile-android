package biz.belcorp.salesforce.modules.inspires.features.travel.tabs

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.InspireAdvanceFragment
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.InspireConditionsFragment
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.ranking.InspireRankingFragment

class TabsPagerAdapter(
    private val context: Context,
    private val limited: Boolean,
    private val active: Boolean,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    private val inspireRankingFragment by lazy { InspireRankingFragment.newInstance() }
    private val inspireAdvanceFragment by lazy { InspireAdvanceFragment.newInstance() }
    private val inspireConditionsFragment by lazy { InspireConditionsFragment.newInstance() }

    override fun getItem(position: Int): Fragment {
        return if (active && limited.not()) {
            when (position) {
                Constant.NUMBER_ZERO -> inspireRankingFragment
                Constant.NUMBER_ONE -> inspireAdvanceFragment
                Constant.NUMBER_TWO -> inspireConditionsFragment
                else -> throw IllegalArgumentException(
                    "Posición de tab inválido. " +
                        "Revisa el método getCount()"
                )
            }
        } else {
            when (position) {
                Constant.NUMBER_ZERO -> inspireAdvanceFragment
                Constant.NUMBER_ONE -> inspireConditionsFragment
                else -> throw IllegalArgumentException(
                    "Posición de tab inválido. " +
                        "Revisa el método getCount()"
                )
            }
        }
    }

    override fun getCount(): Int {
        return if (active && limited.not())
            Constant.NUMBER_THREE
        else
            Constant.NUMBER_TWO
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (active && limited.not())
            when (position) {
                Constant.NUMBER_ZERO -> context.getString(R.string.viaje_inspira_tab_title_1)
                Constant.NUMBER_ONE -> context.getString(R.string.viaje_inspira_tab_title_2)
                Constant.NUMBER_TWO -> context.getString(R.string.viaje_inspira_tab_title_3)
                else -> null
            }
        else
            when (position) {
                Constant.NUMBER_ZERO -> {
                    if (active)
                        context.getString(R.string.viaje_inspira_tab_title_2)
                    else
                        context.getString(R.string.viaje_inspira_tab_title_2_2)
                }
                Constant.NUMBER_ONE -> context.getString(R.string.viaje_inspira_tab_title_3)
                else -> null
            }
    }

}

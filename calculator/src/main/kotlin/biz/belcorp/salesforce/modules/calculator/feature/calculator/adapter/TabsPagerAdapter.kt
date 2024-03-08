package biz.belcorp.salesforce.modules.calculator.feature.calculator.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.Navigator
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection.CampaignProjectionFragment
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection.ProgressProjectionFragment

class TabsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val partnerSection: String?
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var navigator: Navigator? = null

    override fun getItem(position: Int): Fragment {
        return when (position) {
            NUMBER_ZERO -> CampaignProjectionFragment.newInstance(partnerSection)
            NUMBER_ONE -> ProgressProjectionFragment.newInstance(partnerSection)
            else -> throw IllegalArgumentException(
                "Posición de tab inválido. " +
                    "Revisa el método getCount()"
            )
        }
    }

    override fun getCount(): Int = NUMBER_TWO


    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            NUMBER_ZERO -> context.getString(R.string.calculator_dashboard_tab_nivel_actual)
            NUMBER_ONE -> context.getString(R.string.calculator_dashboard_tab_otro_nivel)
            else -> null
        }
    }
}

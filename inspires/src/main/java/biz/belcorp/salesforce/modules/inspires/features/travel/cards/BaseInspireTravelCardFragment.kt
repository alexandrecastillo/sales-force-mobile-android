package biz.belcorp.salesforce.modules.inspires.features.travel.cards

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.inspires.R

abstract class BaseInspireTravelCardFragment : BaseFragment() {

    fun setLevelColor(nivel: String, view: ImageView) {
        context?.let {
            when (nivel) {
                Constant.INSPIRA_PRE_BRONCE -> {
                    view.setColorFilter(R.color.inspira_nivel_pre_bronce)
                }
                Constant.INSPIRA_BRONCE -> {
                    view.setColorFilter(R.color.inspira_nivel_bronce)
                }
                Constant.INSPIRA_PLATA -> {
                    view.setColorFilter(R.color.inspira_nivel_plata)
                }
                Constant.INSPIRA_ORO -> {
                    view.setColorFilter(R.color.inspira_nivel_oro)
                }
                Constant.INSPIRA_PLATINIUM -> {
                    view.setColorFilter(R.color.inspira_nivel_platinium)
                }
                Constant.INSPIRA_DIAMANTE -> {
                    view.setColorFilter(R.color.inspira_nivel_diamante)
                }
            }
        }
    }

    fun makeUp(flagStatus: String?, icon: ImageView, number: TextView) {
        when (flagStatus) {
            UPGRADE -> {
                icon.imageResource = R.drawable.ic_inspira_arrow_up
                number.textColor = ContextCompat.getColor(activity as Context, R.color.inspira_green)
            }
            NO_CHANGE -> {
                icon.visibility = View.INVISIBLE
                number.textColor = ContextCompat.getColor(activity as Context, R.color.black)
            }
            DOWNGRADE -> {
                icon.imageResource = R.drawable.ic_inspira_arrow_down
                number.textColor = ContextCompat.getColor(activity as Context, R.color.red)
            }
        }
    }

    companion object {
        const val UPGRADE: String = "S"
        const val NO_CHANGE: String = "-"
        const val DOWNGRADE: String = "B"
    }
}

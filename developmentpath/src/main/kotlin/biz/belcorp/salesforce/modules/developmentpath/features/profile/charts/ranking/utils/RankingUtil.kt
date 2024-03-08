package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils

import android.graphics.Color
import android.widget.TextView
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.ChartEntryModel
import kotlin.math.roundToInt

object RankingUtil {

    fun getPercentage(txtPercetage: TextView, value: ChartEntryModel) {
        txtPercetage.text = String.format("%d%%", value.firstValue.roundToInt())
        txtPercetage.textColor = if (value.firstValue >= value.maxValue) {
            Color.parseColor("#2abeb3")
        } else {
            Color.parseColor("#99c9ff")
        }
    }

}

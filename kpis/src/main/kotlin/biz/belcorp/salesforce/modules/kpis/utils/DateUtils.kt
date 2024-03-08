package biz.belcorp.salesforce.modules.kpis.utils

import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import java.util.*

object DateUtils {

    fun getLastYear(): Int {
        val date = Calendar.getInstance()
        date.add(Calendar.YEAR, NEGATIVE_NUMBER_ONE)
        return date.get(Calendar.YEAR)
    }
}

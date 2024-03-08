package biz.belcorp.salesforce.messaging.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.NEWS
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.POSTULANTS
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.RDD

object AnalyticUtils {

    fun screen(topic: String) = BelcorpAnalytics.log(getScreen(topic))

    private fun getScreen(topic: String): String =
        when (topic) {
            NEWS -> ScreenTag.NOT_UPDATE
            POSTULANTS -> ScreenTag.NOT_UNETE
            RDD -> ScreenTag.NOT_RDD
            else -> Constant.EMPTY_STRING
        }
}

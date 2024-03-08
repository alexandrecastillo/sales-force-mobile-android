package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun option(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.UNETE,
                category = Category.UNETE,
                action = Action.OPTIONS,
                label = label,
                screenName = ScreenTag.UNETE
            )
        )
    }

    fun screen() = BelcorpAnalytics.log(ScreenTag.UNETE)
}

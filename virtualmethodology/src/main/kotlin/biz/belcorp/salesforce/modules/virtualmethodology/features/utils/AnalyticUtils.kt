package biz.belcorp.salesforce.modules.virtualmethodology.features.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screen() = BelcorpAnalytics.log(ScreenTag.VIRTUAL_METHODOLOGY)

    fun option(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.VIRTUAL_METHODOLOGY,
                category = Category.VIRTUAL_METHODOLOGY,
                action = Action.SELECT,
                label = label,
                screenName = ScreenTag.VIRTUAL_METHODOLOGY
            )
        )
    }
}

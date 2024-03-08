package biz.belcorp.salesforce.modules.orders.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screen() = BelcorpAnalytics.log(ScreenTag.WEB_PED)

    fun send(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.WEB_ORDERS,
                category = Category.WEB_ORDERS,
                action = Action.APPLY_FILTER,
                label = label,
                screenName = ScreenTag.WEB_PED
            )
        )
    }

}

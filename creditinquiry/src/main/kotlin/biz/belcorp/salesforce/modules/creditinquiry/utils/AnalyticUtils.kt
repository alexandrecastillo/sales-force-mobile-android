package biz.belcorp.salesforce.modules.creditinquiry.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screen() = BelcorpAnalytics.log(ScreenTag.CREDIT_INQUIRY)

    fun search(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.CREDIT_INQUIRY,
                category = Category.CREDIT_INQUIRY,
                action = Action.SEARCH,
                label = label,
                screenName = ScreenTag.CREDIT_INQUIRY
            )
        )
    }

}

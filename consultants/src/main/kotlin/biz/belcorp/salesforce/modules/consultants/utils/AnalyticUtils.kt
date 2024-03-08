package biz.belcorp.salesforce.modules.consultants.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screen() = BelcorpAnalytics.log(ScreenTag.SEARCH_CONSULTANT)

    fun filtro(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SEARCH_CONSULTANT,
                category = Category.SEARCH_CONSULTANT,
                action = Action.SEARCH_CONSULTANTS_FILTER,
                label = label,
                screenName = ScreenTag.SEARCH_CONSULTANT
            )
        )
    }

    fun busqueda(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SEARCH_CONSULTANT,
                category = Category.SEARCH_CONSULTANT,
                action = Action.SEARCH_CONSULTANTS,
                label = label,
                screenName = ScreenTag.SEARCH_CONSULTANT
            )
        )
    }

}

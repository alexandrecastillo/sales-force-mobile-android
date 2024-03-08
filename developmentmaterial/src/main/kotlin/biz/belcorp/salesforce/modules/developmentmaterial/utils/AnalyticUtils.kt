package biz.belcorp.salesforce.modules.developmentmaterial.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.Category
import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.EventTag
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screen() = BelcorpAnalytics.log(ScreenTag.DEVELOPMENT_MATERIAL)

    fun materialDesarrollo(action: String, label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.VIRTUAL_METHODOLOGY,
                category = Category.DEVELOPMENT_MATERIAL,
                action = action,
                label = label,
                screenName = ScreenTag.DEVELOPMENT_MATERIAL
            )
        )
    }

}

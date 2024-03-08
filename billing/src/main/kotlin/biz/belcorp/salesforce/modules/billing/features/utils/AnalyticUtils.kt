package biz.belcorp.salesforce.modules.billing.features.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screen() = BelcorpAnalytics.log(ScreenTag.BILLING)

    fun detallePedido(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.ORDER_DETAIL,
                category = Category.BILLING,
                action = Action.ORDER_DETAIL,
                label = label,
                screenName = ScreenTag.BILLING
            )
        )
    }

    fun seleccionaTuZona(zona: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SELECCIONA_TU_ZONA,
                category = Category.BILLING,
                action = Action.SELECCIONA_TU_ZONA,
                label = zona,
                screenName = ScreenTag.BILLING
            )
        )
    }

}

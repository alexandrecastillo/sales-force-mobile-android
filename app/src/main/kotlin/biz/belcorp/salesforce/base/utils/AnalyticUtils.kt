package biz.belcorp.salesforce.base.utils

import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.NOT_AVAILABLE
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics

object AnalyticUtils {

    fun screenMenu() = BelcorpAnalytics.log(ScreenTag.MENU)

    fun screenHome() = BelcorpAnalytics.log(ScreenTag.HOME)

    fun bannerAvanceFacturacion() {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.AVANCE_FACTURACION,
                category = Category.HOME,
                action = Action.SELECCION_AVANCE_FACTURACION,
                label = NOT_AVAILABLE,
                screenName = ScreenTag.HOME
            )
        )
    }

    fun desarrollaTuNegocio(cardSeleccionado: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.DESARROLLA_NEGOCIO,
                category = Category.HOME,
                action = Action.DESARROLLA_NEGOCIO,
                label = cardSeleccionado,
                screenName = ScreenTag.HOME
            )
        )
    }

    fun menuInferior(opcion: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.BOTTOM_MENU,
                category = Category.BOTTOM_MENU,
                action = Action.OPTIONS,
                label = opcion,
                screenName = ScreenTag.HOME
            )
        )
    }

    fun seleccionaTuZona(zona: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SELECCIONA_TU_ZONA,
                category = Category.HOME,
                action = Action.SELECCIONA_TU_ZONA,
                label = zona,
                screenName = Category.HOME
            )
        )
    }

    fun sideMenu(label: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.MORE_OPTIONS,
                category = Category.BOTTOM_MENU_MORE,
                action = Action.OPTIONS,
                label = label,
                screenName = ScreenTag.MENU
            )
        )
    }

}

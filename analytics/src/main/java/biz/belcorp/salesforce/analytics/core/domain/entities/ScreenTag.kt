package biz.belcorp.salesforce.analytics.core.domain.entities

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    ScreenTag.HOME,
    ScreenTag.NEW_CYCLES,
    ScreenTag.BILLING,
    ScreenTag.CAPITALIZATION,
    ScreenTag.SALES,
    ScreenTag.COLLECTION,
    ScreenTag.UNETE,
    ScreenTag.NOT_UPDATE,
    ScreenTag.NOT_UNETE,
    ScreenTag.NOT_RDD,
    ScreenTag.SEARCH_CONSULTANT,
    ScreenTag.WEB_PED,
    ScreenTag.MENU,
    ScreenTag.VIRTUAL_METHODOLOGY,
    ScreenTag.DEVELOPMENT_MATERIAL,
    ScreenTag.SE_ADVANCE,
    ScreenTag.CREDIT_INQUIRY

)
annotation class ScreenTag {
    companion object {
        const val HOME = "Home"
        const val NEW_CYCLES = "ciclo_de_nuevas"
        const val BILLING = "avance_de_facturacion"
        const val CAPITALIZATION = "retencion_y_capitalizacion"
        const val SALES = "pedidos_y_ventas"
        const val COLLECTION = "cobranza"
        const val UNETE = "unete"
        const val NOT_UPDATE = "noficaciones_novedades"
        const val NOT_UNETE = "notificaciones_unete"
        const val NOT_RDD = "notificaciones_rutas"
        const val SEARCH_CONSULTANT = "buscar_consultora"
        const val WEB_PED = "pedidos_web"
        const val MENU = "menu"
        const val VIRTUAL_METHODOLOGY = "metodologia_virtual"
        const val DEVELOPMENT_MATERIAL = "materiales_de_desarrollo"
        const val SE_ADVANCE = "avance_de_socias"
        const val CREDIT_INQUIRY = "consulta_crediticia"
    }
}

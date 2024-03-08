package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import androidx.annotation.StringDef

@StringDef(
    TipDesarrolloIconIdentifier.VENTAGANANCIA,
    TipDesarrolloIconIdentifier.DIGITAL,
    TipDesarrolloIconIdentifier.CONCURSOS,
    TipDesarrolloIconIdentifier.NOVEDADES,
    TipDesarrolloIconIdentifier.PROGRAMANUEVAS,
    TipDesarrolloIconIdentifier.CAMINOBRILLANTE,
    TipDesarrolloIconIdentifier.ESTABLECIDAS
)
annotation class TipDesarrolloIconIdentifier {
    companion object {
        const val VENTAGANANCIA = "0"
        const val DIGITAL = "1"
        const val CONCURSOS = "2"
        const val NOVEDADES = "3"
        const val PROGRAMANUEVAS = "4"
        const val CAMINOBRILLANTE = "5"
        const val ESTABLECIDAS = "6"
    }
}

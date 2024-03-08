package biz.belcorp.salesforce.modules.postulants.core.domain.enums

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    FuenteIngreso.UB,
    FuenteIngreso.MOVIL,
    FuenteIngreso.PORTAL
)
annotation class FuenteIngreso {
    companion object {
        const val UB = "UB"
        const val MOVIL = "MovilSE"
        const val PORTAL = "PortalGZ"
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model

import android.content.Context
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

abstract class RegionModel(val llaveUA: LlaveUA,
                           val codigoCampania: String,
                           val visitasRealizadas: Int,
                           val visitasPlanificadas: Int,
                           val planId: Long,
                           val planValido: Boolean) {

    abstract val mostrarProductividad: Int

    abstract val productividad: String

    open fun construirTitulo(context: Context): String {
        TODO("A implementar")
    }

    open fun construirColorTitulo(context: Context): Int {
        TODO("A implementar")
    }
}

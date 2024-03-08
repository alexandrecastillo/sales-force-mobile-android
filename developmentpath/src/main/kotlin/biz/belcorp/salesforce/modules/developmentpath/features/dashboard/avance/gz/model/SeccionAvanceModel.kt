package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import java.io.Serializable

data class SeccionAvanceModel(val planId: Long?,
                              val llaveUA: LlaveUA,
                              val seccionFuePlanificada: Boolean,
                              val codigoSeccion: String,
                              val coberturada: Boolean,
                              val nombreSocia: String,
                              val nivel: String,
                              val exito: Exito,
                              val estado: String,
                              val visitadas: String,
                              val total: String,
                              val progreso: Int,
                              val visitedDays:Int = 0) : Serializable {


    val planIdValidado get() = requireNotNull(planId) { "El plan id no es válido" }

    class Exito(val titulo: String, val color: Color) : Serializable {
        enum class Color { VERDE, ROJO }
    }
}

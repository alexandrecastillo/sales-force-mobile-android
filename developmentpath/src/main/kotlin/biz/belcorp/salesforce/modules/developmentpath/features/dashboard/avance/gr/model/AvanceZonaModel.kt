package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class AvanceZonaModel(val llaveUA: LlaveUA,
                      val nombreGerente: String,
                      val nombreCortoCampania: String,
                      val visitasRealizadas: Int,
                      val visitasPlanificadas: Int,
                      val estadoProductividad: String?,
                      val estaCoberturada: Boolean = false,
                      val esNueva: Boolean,
                      val planId: Long?,
                      val zonaPlanificada: Boolean) {

    val planIdValidado: Long get() { return checkNotNull(planId) }
}

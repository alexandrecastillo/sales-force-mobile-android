package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa

data class FocosYHabilidadesPorUa(val codigoUa: String,
                                  val personaId: Long?,
                                  val nombresPersona: String,
                                  val codigoCampania: String,
                                  val ua: UnidadAdministrativa,
                                  val coberturada: Boolean,
                                  val mostrarFocos: Boolean,
                                  val mostrarEditarFocos: Boolean,
                                  val mostrarAsignarFocos: Boolean,
                                  val focos: List<FocoModel>,
                                  val mostrarHabilidades: Boolean,
                                  val mostrarEditarHabilidades: Boolean,
                                  val mostrarAsignarHabilidades: Boolean,
                                  val habilidades: List<HabilidadModel>) {

    data class FocoModel(val id: Long, val descripcion: String?)

    data class HabilidadModel(val id: Long, val descripcion: String?)
}

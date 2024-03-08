package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

open class Params(
    val personaId: Long = 0L,
    val personaRol: Rol,
    val opciones: List<String>? = emptyList(),
    val opcion: String = ""
) {
    lateinit var ua: LlaveUA
    var campaniaActual: String = ""
    var filtrarPorNombreOpciones: List<String> = emptyList()
}

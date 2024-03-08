package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import java.util.*

open class EventoRddModeloCreacion(
    val idTipoEvento: Long,
    val fechaInicio: Calendar,
    val fechaFin: Calendar?,
    val esTodoElDia: Boolean,
    val descripcionPersonalizada: String?,
    val compartirObligatorio: Boolean?,
    val ubicacion: String,
    val alertar: Boolean,
    val alerta: Alerta?
) {
    var campania: String = ""
    var usuarioCreacion: String? = null
    var llaveUa: LlaveUA? = null
}

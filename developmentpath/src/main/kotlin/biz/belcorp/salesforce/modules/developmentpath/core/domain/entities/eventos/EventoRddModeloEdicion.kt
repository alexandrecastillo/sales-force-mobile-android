package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import java.util.*

class EventoRddModeloEdicion(
    var id: Long,
    idTipoEvento: Long,
    fechaInicio: Calendar,
    fechaFin: Calendar?,
    esTodoElDia: Boolean,
    descripcionPersonalizada: String?,
    compartirObligatorio: Boolean?,
    ubicacion: String,
    alertar: Boolean,
    alerta: Alerta?
) : EventoRddModeloCreacion(
    idTipoEvento = idTipoEvento,
    fechaInicio = fechaInicio,
    fechaFin = fechaFin,
    esTodoElDia = esTodoElDia,
    descripcionPersonalizada = descripcionPersonalizada,
    compartirObligatorio = compartirObligatorio,
    ubicacion = ubicacion,
    alertar = alertar,
    alerta = alerta
) {
    fun asignarId(id: Long): EventoRddModeloEdicion {
        this.id = id
        return this
    }
}

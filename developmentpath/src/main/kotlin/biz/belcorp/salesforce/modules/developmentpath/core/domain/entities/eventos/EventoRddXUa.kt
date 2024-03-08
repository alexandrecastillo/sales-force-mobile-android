package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class EventoRddXUa(
    val id: Long,
    val uA: LlaveUA
) {

    private lateinit var evento: EventoRdd

    fun setEvento(evento: EventoRdd) {
        this.evento = evento
    }

    fun getEvento(): EventoRdd {
        return evento
    }
}

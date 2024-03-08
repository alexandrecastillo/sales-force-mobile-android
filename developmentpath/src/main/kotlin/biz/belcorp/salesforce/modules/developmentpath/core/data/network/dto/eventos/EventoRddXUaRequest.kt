package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos

import java.util.*

data class EventoRddXUaRequest(
    val eventoRDDPorUAId: Long,
    val asistira: Boolean,
    val usuarioCreacion: String,
    val fechaCreacion: String,
    val fechaCumplimiento: Date?,
    val indicaCumplimiento: Boolean
)

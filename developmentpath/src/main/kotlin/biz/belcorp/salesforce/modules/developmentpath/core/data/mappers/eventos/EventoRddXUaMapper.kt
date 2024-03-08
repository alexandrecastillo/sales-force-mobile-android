package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos.EventoRddXUaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toStringDate
import java.util.*

class EventoRddXUaMapper {

    fun parse(eventos: List<EventoRddXUaEntity>): List<EventoRddXUaRequest> {
        return eventos.map { parse(it) }
    }

    private fun parse(evento: EventoRddXUaEntity): EventoRddXUaRequest {
        return EventoRddXUaRequest(
            eventoRDDPorUAId = evento.idServidor ?: Constant.CERO.toLong(),
            asistira = evento.asistira,
            usuarioCreacion = evento.usuarioModificacion ?: Constant.HYPHEN,
            fechaCreacion = Calendar.getInstance().toStringDate(),
            fechaCumplimiento = evento.fechaCumplimiento.toDate(),
            indicaCumplimiento = evento.indicaCumplimiento
        )
    }

    fun extraerUa(evento: EventoRddXUaEntity): LlaveUA {
        return LlaveUA(
            codigoRegion = evento.region,
            codigoZona = evento.zona,
            codigoSeccion = evento.seccion,
            consultoraId = Constant.MENOS_UNO.toLong()
        )
    }

}

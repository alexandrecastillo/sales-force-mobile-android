package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.model

import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.core.utils.parseToLongDescripitionDayMonthYear
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd

class DetalleEventoMapper {

    fun parse(evento: EventoRdd): DetalleEventoModel {

        return DetalleEventoModel(
                eventoId = evento.id,
                titulo = evento.descripcion ?: "",
                ubicacion = evento.ubicacion.aGuionSiEsNullOVacio(),
                esTodoElDia = evento.esTodoElDia,
                fecha = evento.fechaInicio.time.parseToLongDescripitionDayMonthYear(),
                horaInicio = evento.fechaInicio.time.parseToHoursDescription(),
                horaFin = evento.fechaFin?.time?.parseToHoursDescription() ?: "",
                alerta = evento.alerta,
                registrado = evento.registrar,
                activo = evento.activo)
    }
}

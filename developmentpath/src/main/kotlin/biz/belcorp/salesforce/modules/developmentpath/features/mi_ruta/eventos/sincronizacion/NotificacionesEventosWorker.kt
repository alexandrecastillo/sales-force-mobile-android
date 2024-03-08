package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.sincronizacion

import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventosCambiadosRespuesta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.NotificacionesEventosPresenter
import io.reactivex.Completable
import java.util.*

class NotificacionesEventosWorker(
    private val notificacionEventosHandler: NotificacionEventosHandler,
    private val broadcastEventosHandler: BroadcastEventosHandler
) : NotificacionesEventosPresenter {

    override fun mostrarNotificaciones(respuesta: EventosCambiadosRespuesta): Completable {
        return Completable.create {
            if (respuesta is EventosCambiadosRespuesta.Cambiados &&
                respuesta.existenCambios
            ) {
                notificarEventosCreados(respuesta)
                notificarEventosActualizados(respuesta)
                notificarEventosEliminados(respuesta)
                emitirBroadcastEventos()
            }

            it.onComplete()
        }
    }

    private fun notificarEventosEliminados(t: EventosCambiadosRespuesta.Cambiados) {
        t.eliminados.forEach {
            notificacionEventosHandler.notificarEventoEliminado(parsearEvento(it))
        }
    }

    private fun notificarEventosActualizados(t: EventosCambiadosRespuesta.Cambiados) {
        t.editados.forEach {
            notificacionEventosHandler.notificarEventoActualizado(parsearEvento(it))
        }
    }

    private fun notificarEventosCreados(t: EventosCambiadosRespuesta.Cambiados) {
        t.nuevos.forEach {
            notificacionEventosHandler.notificarEventoCreado(parsearEvento(it))
        }
    }

    private fun emitirBroadcastEventos() {
        broadcastEventosHandler.emitirBroadcastCambioEvento()
    }

    private fun parsearEvento(evento: EventoRdd): ModeloNotificacion {
        return ModeloNotificacion(
            idEvento = evento.id,
            tituloEvento = evento.descripcion ?: "",
            fechaInicio = evento.fechaInicio.time,
            todoElDia = evento.esTodoElDia,
            horas = construirHoras(evento)
        )
    }

    private fun construirHoras(evento: EventoRdd): String {
        return "${evento.fechaInicio.time.parseToHoursDescription()} -" +
            " ${evento.fechaFin?.time?.parseToHoursDescription() ?: ""}"
    }

    class ModeloNotificacion(
        val idEvento: Long,
        val tituloEvento: String,
        val fechaInicio: Date,
        val todoElDia: Boolean,
        val horas: String
    )
}

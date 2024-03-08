package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventosCambiadosRespuesta
import io.reactivex.Completable

interface NotificacionesEventosPresenter {
    fun mostrarNotificaciones(respuesta: EventosCambiadosRespuesta): Completable
}

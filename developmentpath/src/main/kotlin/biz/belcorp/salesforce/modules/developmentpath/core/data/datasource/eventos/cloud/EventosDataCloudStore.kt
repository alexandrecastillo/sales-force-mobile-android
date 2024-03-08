package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos.EventosRddResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.SolicitarEventosCambiadosUseCase
import io.reactivex.Single

class EventosDataCloudStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun enviarNuevos(eventos: List<EventoRddEntity>): Single<EventosRddResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.crearEventos(eventos)
        }
    }

    fun enviarEditados(eventos: List<EventoRddEntity>): Single<EventosRddResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.editarEventos(eventos)
        }
    }

    fun recibirCambios(request: SolicitarEventosCambiadosUseCase.Request): Single<EventosRddResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.obtenerEventos(
                request.fechaEventosSync,
                request.ua,
                request.campania
            )
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.cloud.EventosXUaDataCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.EventosXUaDataLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos.EventoRddXUaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.ConfirmarEventoAtraccionUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.EliminarEventoUseCase
import io.reactivex.Completable
import io.reactivex.Single

class EventoXUaDataRepository(private val localStore: EventosXUaDataLocalStore,
                              private val cloudStore: EventosXUaDataCloudStore,
                              private val mapper: EventoRddXUaMapper
) : EventoXUaRepository {

    override fun rechazar(param: EliminarEventoUseCase.RechazarEventoParams): Completable {
        return localStore.rechazarEvento(param.eventoXUaId, param.usuarioModificacion)
    }

    override fun enviarAServidor(): Completable {
        return localStore.recuperarNoEnviados()
            .map { mapper.parse(it) }
            .filter { it.isNotEmpty() }
            .flatMapCompletable { cloudStore.enviar(it) }
            .andThen(localStore.marcarAEnviados())
    }

    override fun determinarEsOrganizador(eventoXUaId: Long): Single<Boolean> {
        return localStore.determinarEsOrganizador(eventoXUaId)
    }

    override fun recuperarUa(eventoXUaId: Long): Single<LlaveUA> {
        return localStore.recuperar(eventoXUaId).map { mapper.extraerUa(it) }
    }

    override fun recuperarEventoId(eventoXUaId: Long): Single<Long> {
        return localStore.recuperarEventoId(eventoXUaId)
    }

    override fun registrar(param: ConfirmarEventoAtraccionUseCase.RegistrarEventoParams): Completable {
        return localStore.registrarEvento(param)
    }
}

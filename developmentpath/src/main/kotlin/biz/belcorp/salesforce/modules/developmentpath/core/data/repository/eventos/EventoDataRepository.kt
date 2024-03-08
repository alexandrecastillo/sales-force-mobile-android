package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos

import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoTipoEventoJoined
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.cloud.EventosDataCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.EventosDataLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos.EventoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos.EventosRddResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.map
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloCreacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloEdicion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventosCambiadosRespuesta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.CrearEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.SolicitarEventosCambiadosUseCase
import io.reactivex.Completable
import io.reactivex.Single

class EventoDataRepository(
    private val localStore: EventosDataLocalStore,
    private val cloudStore: EventosDataCloudStore,
    private val syncPreferences: SyncSharedPreferences,
    private val mapper: EventoRddMapper
) : EventoRepository {

    override fun crear(evento: EventoRddModeloCreacion): Single<Long> {
        return localStore.crearEvento(mapper.parse(evento))
    }

    override fun crear(eventoXUa: CrearEventoUseCase.GenerarEventoXUaParam): Completable {
        return localStore.crearEventoXUa(mapper.parse(eventoXUa))
    }

    override fun editar(evento: EventoRddModeloEdicion): Completable {
        return localStore.recuperarEventoId(evento.id).flatMapCompletable {
            localStore.editarEvento(mapper.parse(evento.asignarId(it)))
        }
    }

    override fun eliminar(eventoXUaId: Long): Completable {
        return localStore.eliminarEvento(eventoXUaId)
            .andThen(eliminarEventosXUa(eventoXUaId))
    }

    override fun enviarAServidor(): Completable {
        return enviarNuevos().andThen(enviarEditados())
    }

    override fun recuperar(eventoId: Long): Single<EventoRdd> {
        return localStore.recuperar(eventoId).map { mapper.parse(it) }
    }

    override fun recuperarSincrono(eventoXUaId: Long): EventoRdd? {
        return mapper.parse(localStore.recuperarSincrono(eventoXUaId))
    }

    override fun esLlaveUaCreadora(eventoId: Long, llaveUA: LlaveUA): Single<Boolean> {
        return localStore.esUaCreadora(llaveUA, eventoId)
    }

    private fun eliminarEventosXUa(eventoId: Long): Completable {
        return localStore.eliminarEventosXUaPorEventoId(eventoId)
    }

    private fun enviarNuevos(): Completable {
        return localStore.recuperarNuevosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { cloudStore.enviarNuevos(it).toMaybe() }
            .flatMapCompletable { marcarNuevosComoEnviados(it) }
    }

    private fun enviarEditados(): Completable {
        return localStore.recuperarEditadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { cloudStore.enviarEditados(it).toMaybe() }
            .flatMapCompletable { marcarEditadosComoEnviados(it) }
    }

    private fun marcarNuevosComoEnviados(response: EventosRddResponse): Completable {
        return localStore.marcarComoEnviados(response.resultado.eventos)
            .andThen(localStore.guardarEventosXUa(response.resultado.eventosXUa))
    }

    private fun marcarEditadosComoEnviados(response: EventosRddResponse): Completable {
        return localStore.marcarComoEnviados(response.resultado.eventos)
            .andThen(localStore.guardarEventosXUa(response.resultado.eventosXUa))
    }

    override fun recuperar(llaveUA: LlaveUA): List<EventoRdd> {
        return localStore.recuperarPorUa(llaveUA)
            .mapNotNull { mapper.parse(it) }
    }

    override fun obtenerCambios(request: SolicitarEventosCambiadosUseCase.Request): Single<EventosCambiadosRespuesta.Cambiados> {
        return cloudStore.recibirCambios(request)
            .flatMap { guardar(it) }
            .map { triple -> triple.map { mapper.parse(it) } }
            .map { mapper.parseToCambios(it) }
    }

    private fun guardarFechaEventosSync(fecha: Long) {
        syncPreferences.rddEventsSyncDate = fecha
    }

    private fun guardar(response: EventosRddResponse):
        Single<Triple<List<EventoTipoEventoJoined>, List<EventoTipoEventoJoined>, List<EventoTipoEventoJoined>>> {
        return localStore.guardarYRecuperar(
            response.resultado.eventos,
            response.resultado.eventosXUa
        )
            .doAfterTerminate { guardarFechaEventosSync(response.resultado.tiempo) }
    }
}

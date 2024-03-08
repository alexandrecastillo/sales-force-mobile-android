package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloCreacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloEdicion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventosCambiadosRespuesta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.CrearEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.SolicitarEventosCambiadosUseCase
import io.reactivex.Completable
import io.reactivex.Single

interface EventoRepository {

    fun crear(evento: EventoRddModeloCreacion): Single<Long>
    fun crear(eventoXUa: CrearEventoUseCase.GenerarEventoXUaParam): Completable
    fun editar(evento: EventoRddModeloEdicion): Completable
    fun eliminar(eventoXUaId: Long): Completable
    fun enviarAServidor(): Completable
    fun recuperar(llaveUA: LlaveUA): List<EventoRdd>
    fun recuperar(eventoId: Long): Single<EventoRdd>
    fun recuperarSincrono(eventoXUaId: Long): EventoRdd?
    fun esLlaveUaCreadora(eventoId: Long, llaveUA: LlaveUA): Single<Boolean>
    fun obtenerCambios(request: SolicitarEventosCambiadosUseCase.Request): Single<EventosCambiadosRespuesta.Cambiados>
}

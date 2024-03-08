package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.ConfirmarEventoAtraccionUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.EliminarEventoUseCase
import io.reactivex.Completable
import io.reactivex.Single

interface EventoXUaRepository {
    fun rechazar(param: EliminarEventoUseCase.RechazarEventoParams): Completable
    fun registrar(param: ConfirmarEventoAtraccionUseCase.RegistrarEventoParams): Completable
    fun enviarAServidor(): Completable
    fun determinarEsOrganizador(eventoXUaId: Long): Single<Boolean>
    fun recuperarUa(eventoXUaId: Long): Single<LlaveUA>
    fun recuperarEventoId(eventoXUaId: Long): Single<Long>
}

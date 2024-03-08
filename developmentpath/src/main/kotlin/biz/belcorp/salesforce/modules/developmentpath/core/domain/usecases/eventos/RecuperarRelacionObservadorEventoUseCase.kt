package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class RecuperarRelacionObservadorEventoUseCase(private val eventoXUaRepository: EventoXUaRepository,
                                               private val sesionRepository: SessionPersonRepository,
                                               threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun determinarSiEsOrganizador(eventoXUaId: Long): Single<Boolean> {
        return eventoXUaRepository.determinarEsOrganizador(eventoXUaId)
    }

    private fun determinarEventoxUaEsPropio(eventoXUaId: Long): Single<Boolean> {
        return eventoXUaRepository.recuperarUa(eventoXUaId).zipWith(recuperarUaDeSesion(),
            BiFunction { llaveUaPlan: LlaveUA,
                         llaveUaSesion: LlaveUA ->
                llaveUaPlan.isEqual(llaveUaSesion)
            })
    }

    fun determinarTipo(eventoXUaId: Long): Single<Tipo> {

        return determinarSiEsOrganizador(eventoXUaId).zipWith(
            determinarEventoxUaEsPropio(eventoXUaId),
            BiFunction { esCreador: Boolean,
                         esMiEventoXUa: Boolean ->
                buildResponse(esMiEventoXUa = esMiEventoXUa, esCreador = esCreador)
            })
    }

    private fun recuperarUaDeSesion(): Single<LlaveUA> {
        return Single.create {
            val sesion = requireNotNull(sesionRepository.get())
            val ua = sesion.llaveUA.formatHyphenIfNull()
            it.onSuccess(ua)
        }
    }

    sealed class Tipo {
        object OrganizadorViendoEventoPropio : Tipo()
        object OrganizadorViendoEventoAjeno : Tipo()
        object AsistenteViendoEventoPropio : Tipo()
        object AsistenteViendoEventoAjeno : Tipo()
    }

    private fun buildResponse(esMiEventoXUa: Boolean, esCreador: Boolean): Tipo {
        return when {
            esMiEventoXUa && esCreador -> Tipo.OrganizadorViendoEventoPropio
            !esMiEventoXUa && esCreador -> Tipo.OrganizadorViendoEventoAjeno
            esMiEventoXUa && !esCreador -> Tipo.AsistenteViendoEventoPropio
            else -> Tipo.AsistenteViendoEventoAjeno
        }
    }
}

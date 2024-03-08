package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.puntaje

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.suprimirAPartirDeGuion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje.PuntajePremio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajePremioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniasRddUseCase
import io.reactivex.functions.BiFunction

class PuntajePremioUseCase(
    private val puntajePremioRepository: PuntajePremioRepository,
    private val sesionRepository: SessionPersonRepository,
    private val rddPersonaRepository: RddPersonaRepository,
    private val obtenerCampaniasUseCase: ObtenerCampaniasRddUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val rol = Rol.CONSULTORA

    private val sesion: Sesion
        get() = sesionRepository.get()

    fun obtener(personaId: Long, subscriber: BaseSingleObserver<PuntajePremio>) {
        val single = obtenerCampaniasUseCase
            .recuperarCampaniaActual(personaId, rol)
            .zipWith(rddPersonaRepository
                .singleRecuperarPersonaPorId(PersonaRdd.Identificador(personaId, rol)),
                BiFunction { campania: Campania, persona: PersonaRdd ->
                    Pair(campania, persona)
                })
            .flatMap {
                puntajePremioRepository
                    .recuperarPuntajePorConsultora(
                        obtenerRequest(
                            personaId,
                            it.first.codigo, sesion.pais?.codigoIso,
                            obtenerCodigoConsultoraPorPersona(it.second)
                        )
                    )
            }
        execute(single, subscriber)
    }


    fun obtenerCamapniaActual(personaId: Long, subscriber: BaseSingleObserver<Campania>) {
        val single = obtenerCampaniasUseCase
            .recuperarCampaniaActual(personaId, rol)
        execute(single, subscriber)
    }

    private fun obtenerRequest(
        personaId: Long, codigoCampania: String, codigoIso: String?
        , codigoConsultora: String
    ): PuntajePremio.Request {
        return PuntajePremio.Request(
            codigoIso!!, codigoCampania,
            codigoConsultora, personaId.toString()
        )
    }

    private fun obtenerCodigoConsultoraPorPersona(persona: PersonaRdd): String {

        return when (persona) {
            is SociaEmpresariaRdd -> persona.codigo.suprimirAPartirDeGuion()
            is ConsultoraRdd -> persona.codigo.suprimirAPartirDeGuion()
            else -> throw UnsupportedRoleException(rol)
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository
import io.reactivex.SingleObserver

class MostrarIrAReconocerUseCase(
    private val reconocimientosEnCampaniaRepository: ReconocimientosEnCampaniaRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(request: Request) {
        val single = doOnSingle {
            val persona = recuperarPersona(request)
            val reconocimiento = recuperarReconocimientoEnCampaniaActual(persona.llaveUA)
            val sePuedeReconocer = sePuedeReconocer(persona, reconocimiento.realizado)
            Response(sePuedeReconocer)
        }
        execute(single, request.subscriber)
    }

    private fun recuperarReconocimientoEnCampaniaActual(llaveUA: LlaveUA) =
        reconocimientosEnCampaniaRepository.obtenerParaCampaniaActual(llaveUA)

    private fun recuperarPersona(request: Request) =
        requireNotNull(personaRepository.recuperarPersonaPorId(request.personaId,
            request.rol)) {
            "Error al recuperar dueño de acuerdo con el id especificado"
        }

    private fun sePuedeReconocer(persona: PersonaRdd,
                                 reconocimientoRealizado: Boolean): Boolean {
        return seRegistroPorLoMenosUnaVisita(persona) && !reconocimientoRealizado
    }

    private fun seRegistroPorLoMenosUnaVisita(persona: PersonaRdd): Boolean {
        return persona.agenda.tieneAlMenosUnaRegistrada
    }

    data class Request(val personaId: Long,
                       val rol: Rol,
                       val subscriber: SingleObserver<Response>
    )

    data class Response(val sePuedeReconocer: Boolean)
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import io.reactivex.Observable

class MostrarReconocimientoUseCase(
    private val campaniaRepository: CampaniasRepository,
    private val comportamientoRepository: ReconocimientosRepository,
    private val personaRepository: RddPersonaRepository,
    private val habilidadesReconoceRepository: HabilidadesReconoceRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun validarYMostrar(request: Request) {
        val observable = Observable.create<Response> { emitter ->
            val cantidadComportamientos =
                contarComportamientosEnCampania(request.personaId, request.rol)
            val loQueSeDebeMostrar =
                determinarQueSeDebeMostrar(cantidadComportamientos, request.rol)
            val response = Response(
                loQueSeDebeMostrar = loQueSeDebeMostrar,
                personaId = request.personaId,
                cantidadComportamientos = cantidadComportamientos
            )
            emitter.onNext(response)
        }
        execute(observable, request.subscriber)
    }

    private fun contarComportamientosEnCampania(personaId: Long, rol: Rol): Long {
        val persona = requireNotNull(personaRepository.recuperarPersonaPorId(personaId, rol))
        val codigoCampaniaActual =
            checkNotNull(campaniaRepository.obtenerCampaniaActual(persona.llaveUA)?.codigo)
        return when (rol) {
            Rol.CONSULTORA, Rol.SOCIA_EMPRESARIA ->
                comportamientoRepository.contarReconocimientos(personaId, codigoCampaniaActual)
            Rol.GERENTE_ZONA, Rol.GERENTE_REGION ->
                habilidadesReconoceRepository.contarReconocimientos(personaId, codigoCampaniaActual)
            else ->
                throw UnsupportedRoleException(rol)
        }
    }

    private fun determinarQueSeDebeMostrar(
        cantidadComportamientos: Long,
        rol: Rol
    ): LoQueSeDebeMostrar {
        if (cantidadComportamientos > 0) return LoQueSeDebeMostrar.NADA
        return when (rol) {
            Rol.CONSULTORA,
            Rol.SOCIA_EMPRESARIA ->
                LoQueSeDebeMostrar.COMPORTAMIENTOS
            Rol.GERENTE_ZONA,
            Rol.GERENTE_REGION ->
                LoQueSeDebeMostrar.HABILIDADES
            else -> throw UnsupportedRoleException(rol)
        }
    }

    data class Request(
        val personaId: Long,
        val rol: Rol,
        val subscriber: BaseObserver<Response>
    )

    data class Response(
        val loQueSeDebeMostrar: LoQueSeDebeMostrar,
        val personaId: Long,
        val cantidadComportamientos: Long
    )

    enum class LoQueSeDebeMostrar {
        COMPORTAMIENTOS,
        HABILIDADES,
        NADA
    }
}

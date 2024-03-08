package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador

class ReconocerComportamientosUseCase(
    private val comportamientosRepository: ComportamientosRepository,
    private val reconocimientosRepository: ReconocimientosRepository,
    private val personaRepository: RddPersonaRepository,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private lateinit var seleccionador: Seleccionador<Comportamiento>
    private lateinit var persona: PersonaRdd
    private var sesion: Sesion? = null

    fun ejecutar(request: ReconocerInput.Recuperar) {
        val single = doOnSingle {
            recuperarPersona(request)
            recuperarComportamientos(request)
            recuperarSesion()
            generarResponseRecuperar()
        }
        execute(single, request.subscriber)
    }

    private fun generarResponseRecuperar(): ReconocerOutput.Recuperar {
        return ReconocerOutput.Recuperar(
            persona = persona,
            comportamientos = seleccionador.seleccionables,
            totalComportamientos = seleccionador.seleccionables.size,
            totalSeleccionados = seleccionador.seleccionados.size
        )
    }

    private fun recuperarSesion() {
        sesion = requireNotNull(sesionManager.get())
    }

    private fun recuperarPersona(request: ReconocerInput.Recuperar) {
        persona = requireNotNull(
            personaRepository.recuperarPersonaPorId(
                request.personaId,
                request.rol
            )
        )
    }

    private fun recuperarComportamientos(request: ReconocerInput.Recuperar) {
        val comportamientos = comportamientosRepository.obtenerPorRol(request.rol)
        seleccionador = Seleccionador(comportamientos)
    }

    fun invertirSeleccion(request: ReconocerInput.Invertir) {
        val single = doOnSingle {
            seleccionador.invertirSeleccion(request.indice)
            generarResponseRecuperar()
        }
        return execute(single, request.subscriber)
    }

    fun guardarEnCampaniaActual(request: ReconocerInput.Guardar) {
        val completable = doOnCompletable {
            val guardarRequest = construirRequestGuardado()
            reconocimientosRepository.guardar(guardarRequest)
        }.andThen(reconocimientosRepository.sincronizar())
        execute(completable, request.subscriber)
    }

    private fun construirRequestGuardado(): ReconocimientosRepository.GuardarRequest {
        val codigoCampania = persona.unidadAdministrativa.campania.codigo
        val llaveUa = persona.llaveUA
        val idsReconocidos = seleccionador.seleccionados.map { it.elemento.id }
        val idPersonaSesion = sesion?.persona?.id
        return ReconocimientosRepository.GuardarRequest(
            codigoCampania = codigoCampania,
            llaveUA = llaveUa,
            idsReconocidos = idsReconocidos,
            idPersonaSesion = idPersonaSesion
        )
    }
}

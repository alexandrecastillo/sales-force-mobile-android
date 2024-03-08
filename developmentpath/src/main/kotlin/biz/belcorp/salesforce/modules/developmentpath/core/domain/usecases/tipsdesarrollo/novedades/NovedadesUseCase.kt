package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Novedades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.NovedadesRepository

class NovedadesUseCase(
    private val repository: NovedadesRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerNovedades(request: ParamsNovedades) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                request.campaniaActual = it.unidadAdministrativa.campania.codigo
                request.filtrarPorNombreOpciones =
                    obtenerOpciones(request.opciones, Opciones.ND1, Opciones.ND2)
                repository.obtenerNovedades(request)
            }
            .map { establecerLimiteNovedades(it) }
        execute(single, request.observer)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    private fun obtenerOpciones(opciones: List<String>?, vararg groups: String): List<String> {
        return opciones?.filter { it in groups.toList() } ?: emptyList()
    }

    private fun establecerLimiteNovedades(data: List<Novedades>): List<Novedades> {
        data.forEachIndexed { index, item ->
            data[index].detalleNovedades = item.detalleNovedades.take(LIMITE_MEDIA)
        }
        return data
    }

    class ParamsNovedades(
        personaId: Long, personaRol: Rol, opciones: List<String>? = emptyList(),
        val observer: BaseSingleObserver<List<Novedades>>
    ) : Params(personaId, personaRol, opciones, "")

    companion object {
        private const val LIMITE_MEDIA = 3
    }
}

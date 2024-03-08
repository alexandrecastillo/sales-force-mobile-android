package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniasRddUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import io.reactivex.Single

class ObtenerListadoCampanaFinUseCase(
    private val sesionManager: SessionPersonRepository,
    private val campaniasUseCase: ObtenerCampaniasRddUseCase,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(params: Params) {
        val single = obtenerCampaniaYPais(params.personaId, params.rol)
            .flatMap { obtenerListadoCampanasFin(it) }
        execute(single, params.subscriber)
    }

    private fun recuperarPais(): Single<Pais> {
        return doOnSingle { requireNotNull(sesionManager.get()?.pais) }
    }

    private fun recuperarCantidadDeCampaniasPorPais(pais: Pais): Int {
        return if (pais == Pais.PUERTORICO) 13 else 18
    }

    private fun obtenerListadoCampanasFin(campaniaPais: Pair<String, Pais>): Single<List<String>> {
        return doOnSingle {
            val listadoCampanias = mutableListOf<String>()
            var numeroCampaniaActual = campaniaPais.first.takeLast(2).toInt()
            var anioCampana = campaniaPais.first.take(4).toInt()

            for (i in 1..recuperarCantidadDeCampaniasPorPais(campaniaPais.second)) {
                listadoCampanias.add(obtenerCampania(anioCampana, numeroCampaniaActual))
                if (numeroCampaniaActual >= recuperarCantidadDeCampaniasPorPais(campaniaPais.second)) {
                    numeroCampaniaActual = 0
                    anioCampana++
                }
                numeroCampaniaActual++
            }
            return@doOnSingle listadoCampanias
        }
    }

    private fun obtenerCampania(anio: Int, numeroCampana: Int): String {
        return "$anio${completarConCero(numeroCampana)}"
    }

    private fun completarConCero(numero: Int): String {
        return numero.toString().padStart(2, '0')
    }

    private fun obtenerCampaniaYPais(personaId: Long, rol: Rol): Single<Pair<String, Pais>> {
        return campaniasUseCase.recuperarCampaniaActual(personaId, rol)
            .map { it.codigo }
            .doInParallel(recuperarPais())
    }

    class Params(
        val personaId: Long,
        val rol: Rol,
        val subscriber: BaseSingleObserver<List<String>>
    )
}

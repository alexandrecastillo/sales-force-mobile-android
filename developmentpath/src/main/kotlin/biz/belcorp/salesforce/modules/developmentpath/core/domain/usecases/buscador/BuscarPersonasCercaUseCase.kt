package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.buscador

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador.BuscadorPersonasCerca
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import io.reactivex.Observable

class BuscarPersonasCercaUseCase(private val configRddRepository: ConfigRddRepository,
                                 private val rddPlanRepository: RddPlanRepository,
                                 threadExecutor: ThreadExecutor,
                                 postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun buscar(planId: Long,
               personas: List<PersonaRdd>,
               ubicacion: Pair<Double, Double>,
               observer: BaseObserver<Response>
    ) {
        val observable = Observable.create<Response> { subscriber ->

            val rol = rddPlanRepository.obtenerRolDePlan(planId) ?: error("Id de plan inválido")
            val radio = configRddRepository.get(planId)?.radioBusqueda ?: 3000
            val buscador = BuscadorPersonasCerca(personas, ubicacion, radio)
            val personasCerca = buscador.buscarEnRadio()
            val response = Response(rol, personasCerca)

            subscriber.onNext(response)
            subscriber.onComplete()
        }
        execute(observable, observer)
    }

    class Response(val rol: Rol, val personasCerca: List<PersonaRdd>)
}

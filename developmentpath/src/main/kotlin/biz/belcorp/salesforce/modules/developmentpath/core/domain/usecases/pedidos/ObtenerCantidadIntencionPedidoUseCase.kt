package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import io.reactivex.functions.BiFunction

class ObtenerCantidadIntencionPedidoUseCase(private val planRepository: RddPlanRepository,
                                            private val intencionPedidoRepository: IntencionPedidoRepository,
                                            private val obtenerCampaniasUseCase: ObtenerCampaniasUseCase,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtenerCantidadIntencionPedido(planId: Long, subscriber: BaseSingleObserver<Response>) {
        val infoPlan = planRepository.obtenerInfoPlanRdd(planId) ?: return
        val single = intencionPedidoRepository
            .obtenerIntencionDePedido(infoPlan.llaveUA)
            .zipWith(obtenerCampaniasUseCase.recuperarCampaniaActual(infoPlan.llaveUA),
                BiFunction { cantidadIntencionpedido: String, campania: Campania ->
                    Response(cantidadIntencionpedido, campania.obtenerNombreNumerico())
                })

        execute(single, subscriber)
    }

    class Response(val cantidadIntencionpedido: String,
                   val campania: String)

}

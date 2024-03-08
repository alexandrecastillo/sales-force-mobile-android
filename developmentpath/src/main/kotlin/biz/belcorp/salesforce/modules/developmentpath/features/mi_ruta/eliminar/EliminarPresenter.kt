package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.EliminarDePlanUseCase
import java.util.*

class EliminarPresenter(
    private val view: EliminarView,
    private val eliminarDePlanUseCase: EliminarDePlanUseCase
) {

    fun eliminar(visitaId: Long, fechaOriginal: Date) {
        val request = EliminarDePlanUseCase.Request(
            visitaId,
            fechaOriginal,
            EliminarSubscriber()
        )

        eliminarDePlanUseCase.eliminarDePlan(request)
    }

    private inner class EliminarSubscriber : BaseObserver<Unit>() {
        override fun onNext(t: Unit) {
            view.mostrarExito()
            view.notificarCambioPlan()
        }

        override fun onError(exception: Throwable) {
            view.mostrarError(exception.localizedMessage.orEmpty())
        }
    }
}

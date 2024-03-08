package biz.belcorp.salesforce.modules.developmentpath.features.flujo

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dashboard.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.FlujoRddUseCase

class FlujoRddPresenter(private val flujoRddUseCase: FlujoRddUseCase) {

    var flujoDashboardView: FlujoDashboardView? = null

    fun decidirQueMostrar() {
        flujoRddUseCase.obtener(FlujoRddSuscriber())
    }

    private fun mostrarFlujo(flujo: Flujo) {
        when (flujo) {
            is FlujoGR ->
                flujoDashboardView?.irADashboardGr(flujo.idPlan)
            is FlujoGZ ->
                flujoDashboardView?.irADashboardGz(flujo.idPlan)
            is FlujoSE ->
                flujoDashboardView?.irADashboardSe(flujo.idPlan)
            is FlujoDV ->
                flujoDashboardView?.irADashboardDv(flujo.idPlan)
            is FlujoNoDefinido ->
                flujoDashboardView?.mostrarMensaje("No existe flujo RDD para el rol actual")
        }
    }

    private inner class FlujoRddSuscriber : BaseObserver<Flujo>() {

        override fun onError(exception: Throwable) {
            flujoDashboardView?.mostrarMensaje(exception.localizedMessage)
        }

        override fun onNext(t: Flujo) {
            mostrarFlujo(t)
        }
    }
}

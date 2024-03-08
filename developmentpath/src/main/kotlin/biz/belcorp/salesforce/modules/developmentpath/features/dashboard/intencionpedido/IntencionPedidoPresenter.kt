package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos.ObtenerCantidadIntencionPedidoUseCase

class IntencionPedidoPresenter(
    private val view: IntencionPedidoView,
    private val useCase: ObtenerCantidadIntencionPedidoUseCase
) {

    fun obtenerCantidadIntencionPedido(planId: Long) {
        view?.mostrarCargando()
        useCase.obtenerCantidadIntencionPedido(planId, CantidadIntencionPedidoSubscriber())
    }

    inner class CantidadIntencionPedidoSubscriber :
        BaseSingleObserver<ObtenerCantidadIntencionPedidoUseCase.Response>() {

        override fun onSuccess(t: ObtenerCantidadIntencionPedidoUseCase.Response) {
            view?.ocultarCargando()
            view?.pintarCantidadIntencionPedido(t.cantidadIntencionpedido)
            view?.pintarCampania(t.campania)
        }

        override fun onError(e: Throwable) {
            view?.ocultarCargando()
        }
    }
}

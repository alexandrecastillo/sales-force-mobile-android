package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.presenter

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.OrderIndicatorRDD
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.order.GetOrdersUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.RecuperarFechasCampaniaActual
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos.ObtenerCantidadIntencionPedidoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.view.AvanceCampaniaView

class AvanceCampaniaPresenter(
    private val view: AvanceCampaniaView,
    private val fechasCampaniaUseCase: RecuperarFechasCampaniaActual,
    private val obtenerCantidadIntencionPedidoUseCase: ObtenerCantidadIntencionPedidoUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) : BasePresenter() {

    fun recuperar(planId: Long) {
        fechasCampaniaUseCase.ejecutar(planId, FechasObserver())
        doAsync {
            val iData = getOrdersUseCase.getOrderIndicator()

            uiThread {
                pintarPedidos(iData)
            }
        }
    }

    private fun pintarPedidos(orderIndicator: OrderIndicatorRDD) {
        pintarPedidosFacturados(orderIndicator.orders.toString())
        pintarPedidosMeta(orderIndicator.ordersGoal.toString())
        pintarPorcentaje(orderIndicator.fulfillmentsVsOrderObjective.toInt())
    }

    override fun onExceptionHandler(exception: Throwable) {
        super.onExceptionHandler(exception)
        pintarPedidosPorDefecto()
    }

    fun obtenerCantidadIntencionDePedido(planId: Long) {
        obtenerCantidadIntencionPedidoUseCase.obtenerCantidadIntencionPedido(
            planId,
            IntencionPedidoSubscriber()
        )
    }

    private fun pintarFechas(fechas: RecuperarFechasCampaniaActual.FechasCampania) {
        pintarAvanceDiasCampania(fechas.porcentajeAvance)
        determinarTextoDiasRestantes(fechas.diasRestantes)
    }

    private fun determinarTextoDiasRestantes(diasRestantes: Int) {
        if (diasRestantes == 0)
            pintarHoyComoDiaDeCierre()
        else
            pintarDiasRestantesParaCierre(diasRestantes)
    }

    private fun pintarAvanceDiasCampania(porcentaje: Int) {
        view.pintarPorcentaje(porcentaje / 100F)
    }

    private fun pintarDiasRestantesParaCierre(diasRestantes: Int) {
        view.pintarDiasRestantes(diasRestantes)
    }

    private fun pintarHoyComoDiaDeCierre() {
        view.pintarHoyEsCierre()
    }

    private fun pintarPedidosFacturados(cantidad: String) {
        view.pintarPedidosFacturados(cantidad)
    }

    private fun pintarPedidosMeta(cantidad: String) {
        view.pintarMetaFacturados(cantidad)
    }

    private fun ocultarPorcentajeLogrado() {
        view.ocultarPorcentajeLogrado()
    }

    private fun pintarPorcentaje(porcentaje: Int) {
        if (porcentaje == 0) {
            ocultarPorcentajeLogrado()
        } else {
            view.pintarPorcentajeFacturados(porcentaje)
        }
    }

    private fun pintarPedidosPorDefecto() {
        pintarPedidosFacturados("0")
        pintarPedidosMeta("0")
        ocultarPorcentajeLogrado()
    }

    private inner class FechasObserver :
        BaseSingleObserver<RecuperarFechasCampaniaActual.FechasCampania>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: RecuperarFechasCampaniaActual.FechasCampania) = pintarFechas(t)
    }

    private inner class IntencionPedidoSubscriber :
        BaseSingleObserver<ObtenerCantidadIntencionPedidoUseCase.Response>() {
        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onSuccess(t: ObtenerCantidadIntencionPedidoUseCase.Response) {
            view.pintarCantidadIntencionPedido(t.cantidadIntencionpedido)
        }
    }
}

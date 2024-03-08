package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.RecuperarResultadoVisitasUseCase

class ResultadoVisitasPresenter(
    private val view: ResultadoVisitasView,
    private val recuperarResultadoVisitasUseCase: RecuperarResultadoVisitasUseCase,
    private val resultadoVisitasMapper: ResultadoVisitasMapper
) {

    private lateinit var model: ResultadoVisitasModel

    fun recuperarResultado() {
        recuperarResultadoVisitasUseCase.ejecutar(RecuperarResultadoSubscriber())
    }

    private fun mostrarContenedor() {
        view.mostrarContenedor()
    }

    private fun ocultarContenedor() {
        view.ocultarContenedor()
    }

    private fun cargarModelo() {
        if (todasFacturadas()) {
            felicitar()
        } else {
            ocultarFelicitaciones()
            configurarResultados()
        }
    }

    private fun todasFacturadas(): Boolean {
        return model.noFacturadas <= 0
    }

    private fun felicitar() {
        view.mostrarFelicitaciones()
        view.ocultarFacturadas()
        view.ocultarNoFacturadas()
    }

    private fun ocultarFelicitaciones() {
        view.ocultarFelicitaciones()
    }

    private fun configurarResultados() {
        pintarCantidades()
        configurarBotonVerFacturadas()
        view.mostrarFacturadas()
        view.mostrarNoFacturadas()
    }

    private fun pintarCantidades() {
        view.pintarCantidadFacturadas(model.facturadas.toInt())
        view.pintarCantidadNoFacturadas(model.noFacturadas.toInt())
    }

    private fun configurarBotonVerFacturadas() {
        if (model.facturadas > 0) {
            view.mostrarBotonVerFacturadas()
        } else {
            view.ocultarBotonVerFacturadas()
        }
    }

    private inner class RecuperarResultadoSubscriber :
        BaseSingleObserver<RecuperarResultadoVisitasUseCase.Response>() {

        override fun onSuccess(t: RecuperarResultadoVisitasUseCase.Response) {
            doAsync {
                when (t) {
                    is RecuperarResultadoVisitasUseCase.Response.Activo -> {
                        model = resultadoVisitasMapper.parse(t)
                        uiThread {
                            mostrarContenedor()
                            cargarModelo()
                        }
                    }

                    is RecuperarResultadoVisitasUseCase.Response.Inactivo -> {
                        uiThread { ocultarContenedor() }
                    }
                }
            }
        }
    }
}

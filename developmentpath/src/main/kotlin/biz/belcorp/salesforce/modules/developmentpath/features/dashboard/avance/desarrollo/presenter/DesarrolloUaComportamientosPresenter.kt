package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ComportamientoPorcentaje
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.ObtenerComportamientosDetallePorcentajeUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo.RecuperarTituloDesarrolloUaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.model.DesarrolloComportamientoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.DesarrolloUaComportamientosView

class DesarrolloUaComportamientosPresenter(
    private val view: DesarrolloUaComportamientosView,
    private val recuperarTituloDesarrolloUaUseCase: RecuperarTituloDesarrolloUaUseCase,
    private val recuperarHistoricoUseCase: ObtenerComportamientosDetallePorcentajeUseCase,
    private val desarrolloComportamientoMapper: DesarrolloComportamientoMapper
) {

    fun ejecutar(planId: Long) {
        recuperarTituloDesarrolloUaUseCase.ejecutar(planId, RecuperarUaYCampaniaObserver())
        recuperarHistoricoUseCase.obtener(planId, DesarrolloUaComportamientosObserver())
    }

    private inner class DesarrolloUaComportamientosObserver :
        BaseSingleObserver<List<ComportamientoPorcentaje>>() {

        override fun onSuccess(t: List<ComportamientoPorcentaje>) {
            doAsync {
                val modelos = desarrolloComportamientoMapper.map(t)
                uiThread {
                    view.pintarComportamientos(modelos)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }

    private inner class RecuperarUaYCampaniaObserver :
        BaseSingleObserver<RecuperarTituloDesarrolloUaUseCase.Response>() {

        override fun onSuccess(t: RecuperarTituloDesarrolloUaUseCase.Response) {
            t.campaniaActual.periodo?.let {
                view.pintarCampaniaActual(
                    it,
                    t.campaniaActual.nombreCorto
                )
            }
            view.pintarCampaniaAnterior(t.unidadAdministrativa, t.campaniaAnterior.nombreCorto)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }
}

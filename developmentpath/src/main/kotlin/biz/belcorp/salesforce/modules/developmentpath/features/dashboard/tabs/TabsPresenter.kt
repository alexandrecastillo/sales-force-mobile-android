package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.DefinidorDeRutaPropiaUseCase

class TabsPresenter(private val view: TabsView, private val definidorDeRutaPropiaUseCase: DefinidorDeRutaPropiaUseCase) {

    fun validarDueniaDePlan(planId: Long) {
        definidorDeRutaPropiaUseCase.ejecutar(planId, EsRutaPropia())
    }

    private inner class EsRutaPropia : BaseSingleObserver<Boolean>() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onSuccess(t: Boolean) {
            view.configurarTabs(t)
            validarVisibilidadTabs(t)
        }
    }

    private fun validarVisibilidadTabs(esDuenia: Boolean) {
        if (esDuenia) {
            view.mostrarTabs()
        } else {
            view.ocultarTabs()
        }
    }
}

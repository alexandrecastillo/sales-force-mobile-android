package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceSeccionesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.DefinidorDeRutaPropiaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.AvanceModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.AvanceViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view.AvanceView

class AvanceZonaPresenter (
    private val avanceView: AvanceView,
    private val avanceUseCase: AvanceSeccionesUseCase,
    private val definidorDeRutaPropiaUseCase: DefinidorDeRutaPropiaUseCase,
    private val avanceModelMapper: AvanceModelMapper
) {
    lateinit var viewModel: AvanceViewModel

    fun decidirVisibilidadReconocimientos(planId: Long) {
        if (planId != -1L) {
            definidorDeRutaPropiaUseCase.ejecutar(planId, EsRutaPropia())
        } else {
            avanceView.mostrarAreaReconocimientos()
        }
    }

    fun recargarSecciones(planId: Long) {
        avanceUseCase.ejecutar(planId, AvanceSubscriber())
    }

    private fun cargarModelo() {
        with(viewModel) {
            avanceView.cargarSeccionesSE(secciones)
        }
    }

    private inner class AvanceSubscriber : BaseObserver<AvanceSeccionesUseCase.Response>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
        }

        override fun onNext(t: AvanceSeccionesUseCase.Response) {
            doAsync {
                viewModel = avanceModelMapper.map(t)
                uiThread {
                    cargarModelo()
                }
            }
        }
    }

    private inner class EsRutaPropia : BaseSingleObserver<Boolean>() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onSuccess(t: Boolean) {
            if (t) {
                avanceView.mostrarAreaReconocimientos()
            } else {
                avanceView.ocultarAreaReconocimientos()
            }
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceZonasUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.DefinidorDeRutaPropiaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.AvanceZonasMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view.AvanceRegionView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view.AvanceZonasModel

class AvanceRegionPresenter(
    private var view: AvanceRegionView?,
    private val avanceZonasUseCase: AvanceZonasUseCase,
    private val definidorDeRutaPropiaUseCase: DefinidorDeRutaPropiaUseCase,
    private val avanceZonasMapper: AvanceZonasMapper
) {

    private val modelo = AvanceZonasModel()

    fun establecerVista(view: AvanceRegionView) {
        this.view = view
    }

    fun eliminarVista() {
        view = null
    }

    fun recuperarZonas(planId: Long) {
        val request = AvanceZonasUseCase.Request(planId, AvanceZonasSubcriber())

        avanceZonasUseCase.recuperar(request)
    }

    fun decidirVisibilidadDeReconocimientos(planId: Long) {
        definidorDeRutaPropiaUseCase.ejecutar(planId, EsRutaPropia())
    }

    private inner class AvanceZonasSubcriber : BaseSingleObserver<AvanceZonasUseCase.Response>() {

        override fun onSuccess(t: AvanceZonasUseCase.Response) {
            doAsync {
                modelo.zonas = avanceZonasMapper.parse(t.zonas)
                uiThread {
                    view?.pintarAvanceGerentesZona(modelo.zonas)
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
                view?.mostrarReconocimientos()
            } else {
                view?.ocultarReconocimientos()
            }
        }
    }
}

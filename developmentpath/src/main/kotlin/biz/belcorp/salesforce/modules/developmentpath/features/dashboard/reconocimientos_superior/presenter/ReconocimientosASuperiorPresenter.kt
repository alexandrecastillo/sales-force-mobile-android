package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.Reconocimientos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.AdministrarReconocimientosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model.ListaReconocimientosModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.ReconocimientoASuperiorMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view.ReconocimientosASuperiorView

class ReconocimientosASuperiorPresenter(
    private val view: ReconocimientosASuperiorView,
    private val useCase: AdministrarReconocimientosUseCase,
    private val mapper: ReconocimientoASuperiorMapper
) {

    fun recuperar() {
        useCase.obtenerListaReconocimientos(ReconocimientosSubscriber())
    }

    private inner class ReconocimientosSubscriber : BaseSingleObserver<Reconocimientos>() {

        override fun onSuccess(t: Reconocimientos) {
            doAsync {
                val model = mapper.parse(t)
                uiThread {
                    pintar(model)
                }
            }
        }
    }

    private fun pintar(model: ListaReconocimientosModel) {
        pintarDatosDeMadre(model)
        decidirPintarLista(model)
    }

    private fun pintarDatosDeMadre(model: ListaReconocimientosModel) {
        view?.pintarNombre(model.nombreReconocida)
        val ua = "${model.codigoRolReconocida} - ${model.codigoUnidadAdministrativa}"
        view?.pintarUnidadAdministrativa(ua)
    }

    private fun decidirPintarLista(model: ListaReconocimientosModel) {
        if (model.poseeReconocimientos) {
            view?.pintarReconocimientos(model.reconocimientos)
        } else {
            view?.pintarPlaceholder()
        }
    }
}

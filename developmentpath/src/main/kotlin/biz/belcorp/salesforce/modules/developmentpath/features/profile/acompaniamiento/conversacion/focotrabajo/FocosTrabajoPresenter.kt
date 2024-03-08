package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.ObtenerTitulosGraficosGrUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class FocosTrabajoPresenter(
    private val view: FocosTrabajoView,
    private val useCase: ObtenerTitulosGraficosGrUseCase,
    private val mapper: GraficoGrMapper
) {

    fun obtener(personaId: Long, rol: Rol) {
        useCase.obtener(personaId, rol, FocosTrabajoSubscriber())
    }

    inner class FocosTrabajoSubscriber : BaseSingleObserver<List<GraficoGr>>() {
        override fun onSuccess(t: List<GraficoGr>) {
            doAsync {
                val graficos = t.map { mapper.parse(it) }
                uiThread { view.pintar(graficos) }
            }
        }
    }
}

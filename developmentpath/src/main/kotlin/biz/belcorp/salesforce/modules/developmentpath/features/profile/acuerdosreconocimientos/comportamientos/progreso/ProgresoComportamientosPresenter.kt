package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.ReconocimientoAvanceUseCase

class ProgresoComportamientosPresenter(
    private val view: ProgresoComportamientosView,
    private val useCase: ReconocimientoAvanceUseCase,
    private val mapper: ProgresoComportamientosMapper
) {

    fun getComportamientosUltimasCampanias(id: Long, rol: Rol) {
        val request = ReconocimientoAvanceUseCase.Request(id, rol, ComportamientosSubscriber())
        useCase.ejecutar(request)
    }

    private inner class ComportamientosSubscriber :
        BaseSingleObserver<ReconocimientoAvanceUseCase.Response>() {
        override fun onSuccess(t: ReconocimientoAvanceUseCase.Response) {
            val modelos = mapper.map(t.avances)
            view.pintarComportamientosUC(modelos)
        }
    }
}

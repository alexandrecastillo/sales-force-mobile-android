package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.ComportamientosCampaniaActualUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ComportamientosPresenter(
    private val view: ComportamientosView,
    private val useCase: ComportamientosCampaniaActualUseCase,
    private val mapper: ComportamientosMapper
) {

    fun obtenerComportamientosCampaniaActual(personaId: Long, rol: Rol) {
        val subscriber = ObtenerSubscriber()
        val request = ComportamientosCampaniaActualUseCase.Request(personaId, rol, subscriber)
        useCase.obtener(request)
    }

    private fun actualizarVista(model: ComportamientosModel) {
        pintarReconocimientos(model)
        pintarRazonComportamientos(model)
        pintarProgresoReconocimiento(model)
    }

    private fun pintarReconocimientos(modelo: ComportamientosModel) {
        if (modelo.realizado) {
            view.pintarReconocimientos(modelo.reconocimientos)
        } else {
            view.ocultarReconocimientos()
        }
    }

    private fun pintarRazonComportamientos(modelo: ComportamientosModel) {
        view.pintarCumplimiento(modelo.razon)
    }

    private fun pintarProgresoReconocimiento(modelo: ComportamientosModel) {
        view.pintarProgreso(modelo.progresoPorcentaje)
    }

    inner class ObtenerSubscriber : BaseSingleObserver<TodosLosReconocimientosEnCampania>() {
        override fun onSuccess(t: TodosLosReconocimientosEnCampania) {
            doAsync {
                val model = mapper.map(t)
                uiThread { actualizarVista(model) }
            }
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.planificar

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.PlanificarVisitaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.RecuperarVisitaUseCase
import java.util.*

class PlanificarRapidoPresenter(private val recuperarVisitaUseCase: RecuperarVisitaUseCase,
                    private val planificarVisitaUseCase: PlanificarVisitaUseCase
) {

    var view: PlanificarRapidoView? = null

    fun cargarDatosDeVisita(visitaId: Long) {
        val request = RecuperarVisitaUseCase.Request(visitaId, VisitaSubscriber())
        recuperarVisitaUseCase.recuperar(request)
    }

    fun planificar(visitaId: Long, fecha: Date) {
        val request = PlanificarVisitaUseCase.Request(visitaId, fecha, PlanificarObserver())
        planificarVisitaUseCase.planificarPersona(request)
    }

    private inner class VisitaSubscriber: BaseSingleObserver<Visita>() {
        override fun onSuccess(t: Visita) {
            val nombre = t.persona.nombreApellido.toUpperCase(Locale.getDefault())
            view?.pintarNombrePersona(nombre)
        }
    }

    private inner class PlanificarObserver : BaseObserver<Date>() {
        override fun onNext(t: Date) {
            view?.notificarExitoPlanificacion()
            view?.notificarCambioPlan()
            view?.cerrar()
        }

        override fun onError(exception: Throwable) {
            view?.mostrarError(exception.localizedMessage.orEmpty())
        }
    }
}

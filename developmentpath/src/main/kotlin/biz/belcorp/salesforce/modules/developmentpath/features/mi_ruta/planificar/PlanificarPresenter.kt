package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.PlanificarVisitaUseCase
import java.text.SimpleDateFormat
import java.util.*

class PlanificarPresenter(
        private val planificarVisitaUseCase: PlanificarVisitaUseCase
) {

    var view: PlanificarView? = null

    fun planificar(visitaId: Long, fecha: Date) {
        val request = PlanificarVisitaUseCase.Request(visitaId,
                fecha,
                PlanificarSubscriber())

        planificarVisitaUseCase.planificarPersona(request)
    }

    private inner class PlanificarSubscriber : BaseObserver<Date>() {
        override fun onNext(t: Date) {
            view?.mostrarExito(obtenerFecha(t), obtenerHora(t))
            view?.notificarCambioPlan()
        }

        override fun onError(exception: Throwable) {
            view?.mostrarError(exception.localizedMessage)
        }

        private fun obtenerFecha(date: Date): String {
            val df = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

            return df.format(date)
        }

        private fun obtenerHora(date: Date): String {
            val df = SimpleDateFormat("hh:mm a", Locale.getDefault())

            return df.format(date).toLowerCase()
        }
    }
}

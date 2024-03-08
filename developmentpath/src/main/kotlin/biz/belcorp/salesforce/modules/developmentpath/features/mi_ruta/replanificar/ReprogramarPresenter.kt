package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar


import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.ReprogramarVisitaUseCase
import java.text.SimpleDateFormat
import java.util.*

class ReprogramarPresenter(private val reprogramarUseCase: ReprogramarVisitaUseCase) {

    var view: ReprogramarView? = null

    fun replanificar(visitaId: Long,
                     fechaAnterior: Date,
                     fechaNueva: Date) {
        val request = ReprogramarVisitaUseCase.Request(
                visitaId = visitaId,
                fechaReplanificacion = fechaNueva,
                fechaAnterior = fechaAnterior,
                subscriber = ReplanificarSubscriber())

        reprogramarUseCase.replanificar(request)
    }

    private inner class ReplanificarSubscriber : BaseObserver<Date>() {

        override fun onError(exception: Throwable) {
            view?.mostrarError(exception.localizedMessage)
        }

        override fun onNext(t: Date) {
            view?.notificarCambioEnPlanificacion()
            view?.mostrarExito(obtenerFecha(t),
                               obtenerHora(t))
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

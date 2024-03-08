package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.ObtenerGraficosGrUseCase

class GraficosGrPresenter(
    private val view: GraficosGrView,
    private val useCase: ObtenerGraficosGrUseCase
) {

    fun obtener(personaId: Long, rol: Rol) {
        view.mostrarProgressGraficos()
        useCase.obtener(personaId, rol, GraficoGrSubscriber())
    }

    inner class GraficoGrSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            view.ocultarProgressGraficos()
            view.graficosDescargados()
        }

        override fun onError(e: Throwable) {
            view.ocultarProgressGraficos()
            when (e) {
                is NetworkConnectionException -> view.graficoNoDescargadosPorConexionInternet()
                else -> e.printStackTrace()
            }
        }
    }
}

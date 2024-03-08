package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.AcuerdosHistoricosUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class AcuerdosHistoricosPresenter(
    private val view: AcuerdosHistoricosView,
    private val useCase: AcuerdosHistoricosUseCase,
    private val mapper: AcuerdosHistoricosModelMapper
) {

    val viewModel = AcuerdosHistoricosViewModel()

    fun obtenerAcuerdosCampaniaActual(personaId: Long, rol: Rol) {
        val request = AcuerdosHistoricosUseCase
            .ObtenerRequest(personaId, rol, AcuerdosSubscriber())
        useCase.obtener(request)
    }

    fun invertirCumplimiento(acuerdoId: Long) {
        val request = AcuerdosHistoricosUseCase.InvertirRequest(acuerdoId, AcuerdosSubscriber())
        useCase.invertirCumplimiento(request)
    }

    fun eventoAnalitycs(acuerdoId: Long) {
        val request =
            AcuerdosHistoricosUseCase.EventoAnalyticsRequest(acuerdoId, EventoAnalyticsSubscriber())
        useCase.eventoRegistrarAnalytics(request)
    }

    inner class AcuerdosSubscriber : BaseSingleObserver<AcuerdosHistoricosUseCase.Response>() {
        override fun onSuccess(t: AcuerdosHistoricosUseCase.Response) {
            doAsync {
                actualizarModelo(t)
                uiThread { actualizarModeloEnVista() }
            }
        }

        private fun actualizarModelo(t: AcuerdosHistoricosUseCase.Response) {
            viewModel.acuerdosNoCumplidos = mapper.convertir(t.acuerdosNoCumplidosAgrupados)
            viewModel.acuerdosCumplidos = mapper.convertir(t.acuerdosCumplidosAgrupados)
        }

        private fun actualizarModeloEnVista() {
            pintarAcuerdos()
            view.mostrarTituloAcuerdosNoCumplidos()
            calcularVisibilidadTituloAcuerdosCumplidos()
            calcularVisibilidadSinAcuerdos()
            calcularVisibilidadSinAcuerdosCumplidos()
            calcularVisibilidadSinAcuerdosNoCumplidos()
            view.notificarCambioEnAcuerdos()
        }

        private fun pintarAcuerdos() {
            view.pintarAcuerdosNoCumplidos(viewModel.acuerdosNoCumplidos)
            view.pintarAcuerdosCumplidos(viewModel.acuerdosCumplidos)
        }

        private fun calcularVisibilidadSinAcuerdosCumplidos() {
            if (viewModel.mostrarSinAcuerdosCumplidos) {
                view.mostrarSinAcuerdosCumplidos()
            } else {
                view.ocultarSinAcuerdosCumplidos()
            }
        }

        private fun calcularVisibilidadSinAcuerdosNoCumplidos() {
            if (viewModel.mostrarSinAcuerdosNoCumplidos) {
                view.mostrarSinAcuerdosNoCumplidos()
            } else {
                view.ocultarSinAcuerdosNoCumplidos()
            }
        }

        private fun calcularVisibilidadSinAcuerdos() {
            if (viewModel.mostrarSinAcuerdosHistoricos) {
                view.mostrarSinAcuerdos()
            } else {
                view.ocultarSinAcuerdos()
            }
        }

        private fun calcularVisibilidadTituloAcuerdosCumplidos() {
            if (viewModel.mostrarSinAcuerdosHistoricos) {
                view.ocultarTituloAcuerdosCumplidos()
            } else {
                view.mostrarTituloAcuerdosCumplidos()
            }
        }

        override fun onError(e: Throwable) {
            Logger.loge(this.javaClass.simpleName, e.localizedMessage)
        }
    }

    inner class EventoAnalyticsSubscriber :
        BaseSingleObserver<AcuerdosHistoricosUseCase.EventoAnalyticsResponse>() {

        override fun onSuccess(t: AcuerdosHistoricosUseCase.EventoAnalyticsResponse) {
            view.eventoaRegistarAnalytics(t)
        }

        override fun onError(e: Throwable) {
            Logger.loge(this.javaClass.simpleName, e.localizedMessage)
        }
    }
}

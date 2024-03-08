package biz.belcorp.salesforce.modules.developmentpath.features.flujo

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.FlujoCascadaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.IrAMiRutaUseCase

class FlujoCascadaPresenter(
    private val flujoCascadaUseCase: FlujoCascadaUseCase,
    private val irAMiRutaUseCase: IrAMiRutaUseCase
) {

    var flujoDashboardView: FlujoDashboardView? = null
    var flujoMiRutaView: FlujoMiRutaView? = null

    fun verificarDashboardDeOtra(llaveUA: LlaveUA) {
        val request = FlujoCascadaUseCase.Request(llaveUA, CascadaObserver())
        flujoCascadaUseCase.ejecutar(request)
    }

    private inner class CascadaObserver : BaseObserver<FlujoCascadaUseCase.Response>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            flujoDashboardView?.ocultarCargando()
            when (exception) {
                is NetworkConnectionException ->
                    flujoDashboardView?.mostrarErrorConexion()
                is FlujoCascadaUseCase.InvalidPlanException ->
                    flujoDashboardView?.mostrarErrorPlanInvalido(exception.rolUA)
                else ->
                    flujoDashboardView?.mostrarMensaje(exception.localizedMessage)
            }
        }

        override fun onNext(t: FlujoCascadaUseCase.Response) {
            when (t.direccion) {
                FlujoCascadaUseCase.ResultadoCascada.IR_A_DASHBOARD_GR ->
                    flujoDashboardView?.irADashboardGr(t.planId)
                FlujoCascadaUseCase.ResultadoCascada.IR_A_DASHBOARD_GZ ->
                    flujoDashboardView?.irADashboardGz(t.planId)
                FlujoCascadaUseCase.ResultadoCascada.IR_A_MI_RUTA ->
                    irAMiRuta(t.planId)
                FlujoCascadaUseCase.ResultadoCascada.MOSTRAR_CARGANDO ->
                    flujoDashboardView?.mostrarCargando()
                FlujoCascadaUseCase.ResultadoCascada.OCULTAR_CARGANDO ->
                    flujoDashboardView?.ocultarCargando()
                FlujoCascadaUseCase.ResultadoCascada.RECARGAR_DASHBOARD_ORIGEN ->
                    flujoDashboardView?.notificarCambioRDD()
            }
        }
    }

    fun irAMiRuta(planId: Long) {
        irAMiRutaUseCase.ejecutar(planId, IrAMiRutaSubscriber())
    }

    private inner class IrAMiRutaSubscriber : BaseObserver<IrAMiRutaUseCase.Response>() {

        override fun onNext(t: IrAMiRutaUseCase.Response) {
            when (t) {
                is IrAMiRutaUseCase.Response.IrARuta -> flujoMiRutaView?.irARuta(t.planId)
                is IrAMiRutaUseCase.Response.MostrarCargando -> flujoMiRutaView?.mostrarCargando()
                is IrAMiRutaUseCase.Response.OcultarCargando -> flujoMiRutaView?.ocultarCargando()
            }
        }

        override fun onError(exception: Throwable) {
            flujoMiRutaView?.ocultarCargando()

            when (exception) {
                is NetworkConnectionException -> flujoMiRutaView?.mostrarErrorConexion()
                else -> flujoMiRutaView?.mostrarMensaje(exception.localizedMessage)
            }
        }
    }
}

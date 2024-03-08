package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.AcuerdosCampaniaActualUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.AcuerdosHistoricosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.ModificarAcuerdosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModelMapper

class AcuerdosPresenter(
    private val view: AcuerdosView,
    private val mapper: AcuerdoModelMapper,
    private val obtenerAcuerdosCampaniaActualUseCase: AcuerdosCampaniaActualUseCase,
    private val obtenerAcuerdosUseCase: AcuerdosHistoricosUseCase,
    private val modificarAcuerdosUseCase: ModificarAcuerdosUseCase
) {

    val session = UserProperties.session

    fun getHistoryAgreement(personaId: Long, rol: Rol) {
        val request = AcuerdosHistoricosUseCase
            .ObtenerRequest(personaId, rol, ObtenerSubscriber())
        obtenerAcuerdosUseCase.obtener(request)
    }

    fun obtenerAcuerdosCampaniaActual(personaId: Long, rol: Rol) {
        val request = AcuerdosCampaniaActualUseCase
            .Request(personaId, rol, ObtenerCampaniaActualSubscriber())
        obtenerAcuerdosCampaniaActualUseCase.obtener(request)
    }


    fun getLatestAgreement(personaId: Long, rol: Rol) {
        val request = AcuerdosHistoricosUseCase
            .ObtenerRequest(personaId, rol, ObtenerSubscriber())
        obtenerAcuerdosUseCase.obtener(request)
    }

    fun editar(acuerdoModel: AcuerdoModel) {
        modificarAcuerdosUseCase.editar(
            ModificarAcuerdosUseCase.EditarRequest(
                acuerdoId = acuerdoModel.id,
                nuevoContenido = acuerdoModel.descripcion,
                subscriber = EditarSubscriber()
            )
        )
    }

    fun eliminar(acuerdoModel: AcuerdoModel) {
        modificarAcuerdosUseCase.eliminar(
            ModificarAcuerdosUseCase.EliminarRequest(
                acuerdoId = acuerdoModel.id,
                subscriber = EliminarSubscriber()
            )
        )
    }

    inner class ObtenerSubscriber : BaseSingleObserver<AcuerdosHistoricosUseCase.Response>() {

        override fun onSuccess(response: AcuerdosHistoricosUseCase.Response) {
            val modelos = response.acuerdosCumplidos.map { mapper.map(it, true) } + response.acuerdosNoCumplidos.map { mapper.map(it,true) }
            view.pintarAcuerdos(modelos)
        }

        override fun onError(e: Throwable) {
            Logger.loge(this.javaClass.simpleName, e.localizedMessage)
        }
    }

    inner class ObtenerCampaniaActualSubscriber : BaseSingleObserver<AcuerdosCampaniaActualUseCase.Response>() {

        override fun onSuccess(t: AcuerdosCampaniaActualUseCase.Response) {
            val modelos = t.acuerdos.map { mapper.map(it, true) }
            view.pintarAcuerdos(modelos)
            calcularVisibilidadMensajeVacio(t)
        }

        private fun calcularVisibilidadMensajeVacio(t: AcuerdosCampaniaActualUseCase.Response) {
            if (t.acuerdos.isEmpty()) {
                view.ocultarAcuerdos()
                view.mostrarMensajeVacio()
            } else {
                view.mostrarAcuerdos()
                view.ocultarMensajeVacio()
            }
        }

        override fun onError(e: Throwable) {
            Logger.loge(this.javaClass.simpleName, e.localizedMessage)
        }
    }


    private inner class EditarSubscriber : BaseCompletableObserver() {
        override fun onError(e: Throwable) {
            view.mostrarMensaje(e.localizedMessage)
        }
    }

    private inner class EliminarSubscriber : BaseCompletableObserver() {
        override fun onError(e: Throwable) {
            view.mostrarMensaje(e.localizedMessage)
        }
    }
}

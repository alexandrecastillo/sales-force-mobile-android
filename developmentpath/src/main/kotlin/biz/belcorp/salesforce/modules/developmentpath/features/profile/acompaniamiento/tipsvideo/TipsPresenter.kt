package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.ObtenerTipsVisitaUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class TipsPresenter(
    private val useCase: ObtenerTipsVisitaUseCase,
    private val mapper: TipsMapper
) {

    var view: TipsView? = null
    lateinit var viewModel: TipsViewModel

    var personaId: Long = -1
    lateinit var rol: Rol

    fun obtener(personaId: Long, rol: Rol) {
        val request = ObtenerTipsVisitaUseCase
            .Request(personaId, rol, ObtenerTipsSubscriber())
        useCase.ejecutar(request)
    }

    private inner class ObtenerTipsSubscriber : BaseObserver<ObtenerTipsVisitaUseCase.Response>() {
        override fun onNext(t: ObtenerTipsVisitaUseCase.Response) {
            doAsync {
                viewModel = mapper.map(t.grupoTipsVisita)
                uiThread {
                    view?.cargarModelo(viewModel)
                }
            }
        }

        override fun onError(exception: Throwable) {
            view?.mostrarMensaje(exception.localizedMessage)
        }
    }
}

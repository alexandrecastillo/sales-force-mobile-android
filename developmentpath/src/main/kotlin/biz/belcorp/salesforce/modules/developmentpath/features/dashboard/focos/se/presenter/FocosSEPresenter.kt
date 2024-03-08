package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.presenter

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.ObtenerFocosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.model.FocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view.FocosSEView

class FocosSEPresenter(private val view: FocosSEView,private val obtenerFocosUseCase: ObtenerFocosUseCase) {

    fun inicializar() {
        obtenerFocosUseCase.recuperarFocosParaSocia(RecuperarFocosSubscriber())
    }

    private fun parse(foco: Foco) = FocoModel(foco.descripcion.orEmpty())


    private inner class RecuperarFocosSubscriber : BaseObserver<List<Foco>>() {
        override fun onNext(t: List<Foco>) {
            decidirConRespuesta(t)
        }

        override fun onError(exception: Throwable) {
            Logger.loge(FocosSEPresenter::class.java.simpleName, exception.localizedMessage)
        }
    }

    private fun decidirConRespuesta(focos: List<Foco>) {
        if (focos.isNotEmpty())
            view.pintarFocos(focos.map { parse(it) })
        else
            view.mostrarPlaceHolder()
    }
}

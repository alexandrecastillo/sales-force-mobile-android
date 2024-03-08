package biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarView

class GuardarSubscriber(val view: AsignarView?) : BaseObserver<Unit>() {

    override fun onComplete() = Unit

    override fun onError(exception: Throwable) {
        view?.mostrarMensaje(exception.localizedMessage)
    }

    override fun onNext(t: Unit) {
        view?.notificarActualizacionEnFocosDeHijas()
        view?.cerrar()
    }
}

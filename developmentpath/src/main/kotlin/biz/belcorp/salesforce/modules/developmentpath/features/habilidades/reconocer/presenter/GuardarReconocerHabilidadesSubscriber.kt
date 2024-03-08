package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view.ReconocerHabilidadesView

class GuardarReconocerHabilidadesSubscriber(val view: ReconocerHabilidadesView?) :
    BaseCompletableObserver() {

    override fun onComplete() {
        view?.recargar()
        view?.cerrar()
    }
}

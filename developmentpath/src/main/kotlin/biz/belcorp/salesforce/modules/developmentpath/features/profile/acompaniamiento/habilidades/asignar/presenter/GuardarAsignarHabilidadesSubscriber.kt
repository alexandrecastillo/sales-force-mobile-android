package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadView

class GuardarAsignarHabilidadesSubscriber(val view: AsignarHabilidadView?) : BaseSingleObserver<Unit>() {

    override fun onSuccess(t: Unit) {
        view?.recargarTabFocos()
        view?.cerrar()
    }
}

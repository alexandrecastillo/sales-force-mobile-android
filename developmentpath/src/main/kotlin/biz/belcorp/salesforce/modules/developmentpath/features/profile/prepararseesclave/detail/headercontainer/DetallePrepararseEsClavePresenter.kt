package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.PrepararseEsClaveUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveMapper
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class DetallePrepararseEsClavePresenter(
    private val view: DetallePrepararseEsClaveView,
    private val useCase: PrepararseEsClaveUseCase,
    private val mapper: PrepararseEsClaveMapper
) {

    fun obtener(personaId: Long, rol: Rol) {
        useCase.obtenerDatos(
            PrepararseEsClaveUseCase.Request(
                personaId, rol,
                PrepararseEsClaveSubscriber()
            )
        )
    }

    fun moverAOpcionSeleccionada() {
        view.moverAOpcionSeleccionada()
    }

    fun establecerOpcionSeleccionada() {
        view.establecerOpcionSeleccionada()
    }

    private inner class PrepararseEsClaveSubscriber :
        BaseSingleObserver<List<PrepararseEsClave>>() {
        override fun onSuccess(t: List<PrepararseEsClave>) {
            doAsync {
                val elementos = t.map { mapper.parse(it) }
                uiThread {
                    establecerOpcionSeleccionada()
                    view.mostrarElementos(elementos)
                    moverAOpcionSeleccionada()
                }
            }
        }
    }
}

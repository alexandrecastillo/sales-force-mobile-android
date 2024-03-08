package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.PrepararseEsClaveUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class PrepararseEsClavePresenter(
    private val view: PrepararseEsClaveContract.View,
    private val useCase: PrepararseEsClaveUseCase,
    private val mapper: PrepararseEsClaveMapper
) : PrepararseEsClaveContract.Presenter {

    override fun obtener(personaId: Long, rol: Rol) {
        useCase.obtenerDatos(
            PrepararseEsClaveUseCase.Request(
                personaId,
                rol,
                PrepararseEsClaveSubscriber()
            )
        )
    }

    override fun pintarDescripcionPorRol(rol: Rol) {
        val nombres = rol.comoTexto().split(" ")
        val descripcion = if (nombres.isNotEmpty()) nombres[0] else ""
        view.mostrartDescripcion(descripcion)

    }

    private inner class PrepararseEsClaveSubscriber :
        BaseSingleObserver<List<PrepararseEsClave>>() {
        override fun onSuccess(t: List<PrepararseEsClave>) {
            doAsync {
                val elementos = mapper.parse(t)
                uiThread {
                    view.mostrarData(elementos)
                }
            }
        }
    }
}

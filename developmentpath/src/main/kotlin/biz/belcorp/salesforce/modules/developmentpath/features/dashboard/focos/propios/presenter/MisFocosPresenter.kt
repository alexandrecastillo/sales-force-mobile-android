package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.PermitirAutoasignacionFocosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.RecuperarMisFocosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.model.MiFocoViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view.MisFocosView

class MisFocosPresenter(
    private val view: MisFocosView,
    private val recuperarMisFocosUseCase: RecuperarMisFocosUseCase,
    private val permitirAutoasignacionFocosUseCase: PermitirAutoasignacionFocosUseCase,
    private val mapper: MiFocoViewMapper
) {

    fun establecerVista(view: MisFocosView) {
        validarAutoasignacion()
        recuperarFocos()
    }

    private fun recuperarFocos() =
        recuperarMisFocosUseCase.recuperar(RecuperarMisFocosSubscriber())

    private fun validarAutoasignacion() =
        permitirAutoasignacionFocosUseCase.validar(PermisoAutoasignacionSubscriber())

    fun recuperarRefrescarFocos() = recuperarFocos()

    inner class RecuperarMisFocosSubscriber : BaseSingleObserver<List<Foco>>() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onSuccess(t: List<Foco>) {
            if (t.isNotEmpty()) {
                doAsync {
                    val modelos = t.map { mapper.parse(it) }
                    uiThread {
                        view.pintarFocos(modelos)
                    }
                }
            }
        }
    }

    fun obtenerRol(): String {
        return recuperarMisFocosUseCase.obtenerRol()

    }

    inner class PermisoAutoasignacionSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: Boolean) {
            if (t) view.permitirAsignacion()
        }
    }
}

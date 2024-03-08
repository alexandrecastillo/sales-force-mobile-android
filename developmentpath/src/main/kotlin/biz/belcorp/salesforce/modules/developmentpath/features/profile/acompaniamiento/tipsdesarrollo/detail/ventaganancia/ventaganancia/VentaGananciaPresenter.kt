package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.ventaganancia.VentaGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.ventaganancia.VentaGananciaUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class VentaGananciaPresenter(
    private val view: VentaGananciaContract.View,
    private val useCase: VentaGananciaUseCase,
    private val mapper: VentaGananciaModelMapper
) : VentaGananciaContract.Presenter {

    override fun obtener(params: Params) {
        obtenerVentaGanancia(params)
    }

    private fun obtenerVentaGanancia(params: Params) {
        val opcion = obtenerOpcion(Opciones.VG1, params.opciones ?: return)
        val request = VentaGananciaUseCase.Request(
            params.personaId,
            params.personaRol, opcion ?: return, ObtenerVentaGananciaObserver()
        )
        useCase.obtenerVentaGanancia(request)
    }

    private fun obtenerOpcion(opcion: String, opciones: List<String>): String? {
        return opciones.firstOrNull { it == opcion }
    }

    inner class ObtenerVentaGananciaObserver : BaseSingleObserver<VentaGanancia>() {
        override fun onSuccess(t: VentaGanancia) {
            doAsync {
                val model = mapper.parse(t)
                uiThread {
                    mostrarVentaGananciaEnVista(model)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage)
        }

        private fun mostrarVentaGananciaEnVista(model: VentaGananciaModel) {
            if (model.esValido) view.mostrarVentaGanancia(model)
            else view.mostrarVentaGananciaVacio()
        }
    }

    companion object {
        private val TAG = VentaGananciaPresenter::class.java.simpleName
    }
}

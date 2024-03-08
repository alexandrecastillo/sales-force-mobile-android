package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital.HerramientaDigital
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.digital.HerramientaDigitalUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class HerramientaDigitalPresenter(
    private val view: HerramientaDigitalContract.View,
    private val useCase: HerramientaDigitalUseCase,
    private val mapper: HerramientaDigitalViewMapper
) : HerramientaDigitalContract.Presenter {

    override fun obtener(params: Params) {
        obtenerHerramientasDigitales(params)
    }

    private fun obtenerHerramientasDigitales(params: Params) {
        val opcion = obtenerOpcion(Opciones.DG1, params.opciones ?: return)
        val request = HerramientaDigitalUseCase.DigitalRequest(
            params.personaId,
            params.personaRol, opcion ?: return, ObtenerHerramientasDigitalesObserver()
        )
        useCase.obtenerHerramientasDigitales(request)
    }

    private fun obtenerOpcion(option: String, opciones: List<String>): String? {
        return opciones.firstOrNull { it == option }
    }

    private inner class ObtenerHerramientasDigitalesObserver :
        BaseSingleObserver<List<HerramientaDigital>>() {
        override fun onSuccess(t: List<HerramientaDigital>) {
            doAsync {
                val mapped = mapper.map(t)
                uiThread {
                    if (!t.isNullOrEmpty()) view.mostrarHerramientasDigitales(mapped)
                    else view.mostrarHerramientasDigitalesVacio()
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage)
        }
    }

    companion object {
        private val TAG = HerramientaDigitalPresenter::class.java.simpleName
    }
}

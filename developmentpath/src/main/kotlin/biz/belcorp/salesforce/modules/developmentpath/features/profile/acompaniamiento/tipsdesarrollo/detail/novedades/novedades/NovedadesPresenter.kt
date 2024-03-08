package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Novedades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades.NovedadesUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class NovedadesPresenter(
    private val view: NovedadesView,
    private val useCase: NovedadesUseCase,
    private val mapper: ListaNovedadesMapper
) {

    fun obtenerData(params: Params) {
        obtenerNovedades(params)
    }

    private fun obtenerNovedades(params: Params) {
        val request = NovedadesUseCase.ParamsNovedades(
            params.personaId,
            params.personaRol, params.opciones ?: return, NovedadesSubscriber()
        )
        useCase.obtenerNovedades(request)
    }

    inner class NovedadesSubscriber : BaseSingleObserver<List<Novedades>>() {
        override fun onSuccess(t: List<Novedades>) {
            doAsync {
                val novedades = mapper.map(t)
                uiThread {
                    mostrarNovedadesEnVista(novedades)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage)
        }

        private fun mostrarNovedadesEnVista(novedades: List<ListadoNovedadesModel>) {
            if (novedades.isNotEmpty()) view.pintarNovedades(novedades)
            else view.pintarNovedadesVacio()
        }
    }

    companion object {
        private val TAG = NovedadesPresenter::class.java.simpleName
    }
}

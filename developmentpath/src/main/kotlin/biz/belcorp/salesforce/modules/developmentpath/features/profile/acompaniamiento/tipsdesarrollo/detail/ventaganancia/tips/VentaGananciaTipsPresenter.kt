package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.TipOferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.TipsOfertaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper.TipOfertaViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.TipOfertaModel
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class VentaGananciaTipsPresenter(
    private val view: VentaGananciaTipsView,
    private val useCase: TipsOfertaUseCase,
    private val mapper: TipOfertaViewMapper
) {

    fun obtener(params: Params) {
        view.mostrarProgreso(true)
        val request = TipsOfertaUseCase.Request(
            params.personaId, params.personaRol, params.offline,
            ObtenerCobranzaGananciaObserver()
        )
        useCase.obtenerTipsOfertas(request)
    }

    private inner class ObtenerCobranzaGananciaObserver :
        BaseObserver<List<TipOferta<List<Oferta>>>>() {
        override fun onNext(t: List<TipOferta<List<Oferta>>>) {
            view.mostrarProgreso(false)
            doAsync {
                val tips = mapper.map(t)
                uiThread {
                    mostrarTipsEnVista(tips)
                }
            }
        }

        override fun onError(e: Throwable) {
            view.mostrarProgreso(false)
            mostrarTipsEnVista(emptyList())
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage)
        }

        private fun mostrarTipsEnVista(tips: List<TipOfertaModel>) {
            if (!tips.isNullOrEmpty()) view.mostrarTipsGanaMas(tips)
            else view.mostrarTipsGanaMasVacio()
        }
    }

    companion object {
        private val TAG = VentaGananciaTipsPresenter::class.java.simpleName
    }
}

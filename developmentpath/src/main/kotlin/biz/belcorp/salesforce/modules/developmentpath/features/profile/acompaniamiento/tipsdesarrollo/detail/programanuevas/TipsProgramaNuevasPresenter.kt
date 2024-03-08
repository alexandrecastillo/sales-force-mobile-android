package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.ObtenerTipsVisitaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsContract

class TipsProgramaNuevasPresenter(
    private val view: TipsContract.View,
    private val useCase: ObtenerTipsVisitaUseCase,
    private val tipsMapper: TipsProgramaNuevasMapper
) {

    fun obtener(personaId: Long, rol: Rol) {
        val request = ObtenerTipsVisitaUseCase
            .Request(personaId, rol, ObtenerTipsProgamasNuevasubscriber())
        useCase.ejecutar(request)
    }

    private inner class ObtenerTipsProgamasNuevasubscriber :
        BaseObserver<ObtenerTipsVisitaUseCase.Response>() {
        override fun onNext(t: ObtenerTipsVisitaUseCase.Response) {
            val tips = tipsMapper.map(t.grupoTipsVisita).tips
            if (!tips.isNullOrEmpty()) view.mostrarTips(tips)
            else view.mostrarTipsVacio()
            val video = tipsMapper.map(t.grupoTipsVisita).video
            if (!video.videoUrl.isNullOrEmpty()) {
                view.mostrarVideo(video)
            } else {
                view.mostrarVideoVacio()
            }
        }

        override fun onError(e: Throwable) {
            view.mostrarTipsVacio()
            Logger.loge(TAG, e.localizedMessage)
        }
    }

    companion object {
        private val TAG = TipsProgramaNuevasPresenter::class.java.simpleName
    }
}

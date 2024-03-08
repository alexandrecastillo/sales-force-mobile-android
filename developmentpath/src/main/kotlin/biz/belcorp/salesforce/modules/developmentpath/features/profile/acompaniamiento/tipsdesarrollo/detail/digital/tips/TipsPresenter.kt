package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.digital.HerramientaDigitalUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.CaminoBrillanteTipsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.VentaGananciaTipsUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.mapper.TipViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.mapper.VideoViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class TipsPresenter constructor(
    private val view: TipsContract.View,
    private val ventaGananciaTipsUseCase: VentaGananciaTipsUseCase,
    private val digitalUseCase: HerramientaDigitalUseCase,
    private val caminoBrillanteUseCase: CaminoBrillanteTipsUseCase,
    private val tipMapper: TipViewMapper,
    private val videoMapper: VideoViewMapper
) : TipsContract.Presenter {

    override fun obtener(params: Params) {
        obtenerTips(params)
    }

    private fun obtenerTips(params: Params) {
        val opcion = params.opciones?.firstOrNull()
        when (opcion) {
            Opciones.VG2 -> {
                val request = VentaGananciaTipsUseCase.Request(
                    params.personaId,
                    params.personaRol, opcion, ObtenerTipsVideosObserver()
                )
                ventaGananciaTipsUseCase.obtenerTips(request)
            }
            Opciones.DG1 -> {
                val request = HerramientaDigitalUseCase.TipsRequest(
                    params.personaId,
                    params.personaRol, opcion, ObtenerTipsVideosObserver()
                )
                digitalUseCase.obtenerTipsVideos(request)
            }
            Opciones.CB2 -> {
                val request = CaminoBrillanteTipsUseCase.Request(
                    params.personaId,
                    params.personaRol, opcion, ObtenerTipsVideosObserver()
                )
                caminoBrillanteUseCase.obtenerTips(request)
            }
        }
    }

    private inner class ObtenerTipsVideosObserver :
        BaseSingleObserver<Pair<List<Tip>, List<Video>>>() {
        override fun onSuccess(t: Pair<List<Tip>, List<Video>>) {
            doAsync {
                val tips = tipMapper.map(t.first)
                val videos = videoMapper.map(t.second)
                uiThread {
                    mostrarTipsEnVista(tips)
                    mostrarVideosEnVista(videos)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage)
        }

        private fun mostrarTipsEnVista(tips: List<TipModel>) {
            if (!tips.isNullOrEmpty()) view.mostrarTips(tips)
            else view.mostrarTipsVacio()
        }

        private fun mostrarVideosEnVista(videos: List<VideoModel>) {
            if (!videos.isNullOrEmpty()) {
                val primerVideo = videos[0]
                view.mostrarVideo(primerVideo)
            } else {
                view.mostrarVideoVacio()
            }
        }
    }

    companion object {
        private val TAG = TipsPresenter::class.java.simpleName
    }
}

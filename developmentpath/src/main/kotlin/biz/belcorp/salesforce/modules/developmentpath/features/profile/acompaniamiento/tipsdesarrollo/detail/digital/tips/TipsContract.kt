package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel

interface TipsContract {
    interface View {
        fun mostrarTips(data: List<TipModel>)
        fun mostrarTipsVacio()
        fun mostrarVideo(video: VideoModel)
        fun mostrarVideoVacio()
    }
    interface Presenter {
        fun obtener(params: Params)
    }
}

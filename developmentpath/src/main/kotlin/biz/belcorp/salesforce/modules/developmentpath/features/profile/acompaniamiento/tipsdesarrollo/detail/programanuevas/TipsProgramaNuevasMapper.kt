package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.TipVisita
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel
import biz.belcorp.salesforce.modules.developmentpath.utils.getIdYoutube

class TipsProgramaNuevasMapper {
    fun map(entity: GrupoTipsVisita): ProgramaNuevasModel {
        return ProgramaNuevasModel(
            entity.tips.map { mapTips(it) },
            mapVideo(entity.urlVideo ?: Constant.EMPTY_STRING)
        )
    }

    fun mapTips(tip: TipVisita): TipModel {
        return TipModel(
            tip.id,
            tip.descripcion,
            emptyList()
        )
    }

    fun mapVideo(video: String) = VideoModel(
        video,
        video.getIdYoutube(),
        null
    )
}

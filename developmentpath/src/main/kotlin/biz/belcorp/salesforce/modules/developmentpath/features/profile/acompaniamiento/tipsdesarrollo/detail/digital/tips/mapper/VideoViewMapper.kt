package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel
import biz.belcorp.salesforce.modules.developmentpath.utils.getIdYoutube

class VideoViewMapper : Mapper<Video, VideoModel>() {

    override fun map(value: Video): VideoModel {
        return VideoModel(
            videoUrl = value.url,
            videoId = value.url.getIdYoutube(),
            descripcion = value.descripcion
        )
    }

    override fun reverseMap(value: VideoModel) = Video()
}

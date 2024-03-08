package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.VideoEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video

class VideoEntityMapper : Mapper<VideoEntity, Video>() {
    override fun map(value: VideoEntity): Video {
        return Video(
            id = value.id,
            descripcion = value.descripcion,
            url = value.url)
    }

    override fun reverseMap(value: Video): VideoEntity {
        return VideoEntity(
            id = value.id,
            descripcion = value.descripcion,
            url = value.url
        )
    }
}

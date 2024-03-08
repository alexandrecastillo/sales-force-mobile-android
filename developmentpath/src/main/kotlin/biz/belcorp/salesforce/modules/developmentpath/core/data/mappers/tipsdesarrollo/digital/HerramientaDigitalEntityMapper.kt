package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.digital

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.digital.HerramientaDigitalEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.TipEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.VideoEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital.HerramientaDigital

class HerramientaDigitalEntityMapper(
    private val tipMapper: TipEntityMapper,
    private val videoMapper: VideoEntityMapper
) : Mapper<HerramientaDigitalEntity, HerramientaDigital>() {

    override fun map(value: HerramientaDigitalEntity): HerramientaDigital {
        return HerramientaDigital(
            id = value.id,
            nombre = value.nombre,
            usa = value.usa,
            tips = tipMapper.map(value.tips),
            videos = videoMapper.map(value.videos)
        )
    }

    override fun reverseMap(value: HerramientaDigital): HerramientaDigitalEntity {
        return HerramientaDigitalEntity(
            id = value.id,
            nombre = value.nombre,
            usa = value.usa,
            tips = tipMapper.reverseMap(value.tips),
            videos = videoMapper.reverseMap(value.videos)
        )
    }

}

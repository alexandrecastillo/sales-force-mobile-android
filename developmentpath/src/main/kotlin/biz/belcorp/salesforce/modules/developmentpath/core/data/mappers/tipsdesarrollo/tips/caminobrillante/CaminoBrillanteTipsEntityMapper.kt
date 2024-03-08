package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.caminobrillante

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.caminobrillante.CaminoBrillanteTipEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.TipEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.VideoEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.caminobrillante.CaminoBrillanteTip

class CaminoBrillanteTipsEntityMapper(
    private val tipMapper: TipEntityMapper,
    private val videoMapper: VideoEntityMapper
) : Mapper<CaminoBrillanteTipEntity, CaminoBrillanteTip>() {

    override fun map(value: CaminoBrillanteTipEntity): CaminoBrillanteTip {
        return CaminoBrillanteTip(
            tips = tipMapper.map(value.tips),
            videos = videoMapper.map(value.videos))
    }

    override fun reverseMap(value: CaminoBrillanteTip): CaminoBrillanteTipEntity {
        return CaminoBrillanteTipEntity(
            tips = tipMapper.reverseMap(value.tips),
            videos = videoMapper.reverseMap(value.videos))
    }
}

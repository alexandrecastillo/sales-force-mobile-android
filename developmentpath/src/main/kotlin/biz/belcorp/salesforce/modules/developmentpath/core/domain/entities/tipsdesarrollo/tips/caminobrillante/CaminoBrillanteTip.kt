package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.caminobrillante

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video

data class CaminoBrillanteTip(
    val tips: List<Tip> = emptyList(),
    val videos: List<Video> = emptyList()
)

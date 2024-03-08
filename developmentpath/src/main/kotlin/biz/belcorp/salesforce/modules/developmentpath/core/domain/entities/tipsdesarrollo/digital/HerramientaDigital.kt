package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video

data class HerramientaDigital(
    val id: Long = 0,
    val nombre: String? = null,
    val usa: Boolean = false,
    var tips: List<Tip> = emptyList(),
    var videos: List<Video> = emptyList()
)

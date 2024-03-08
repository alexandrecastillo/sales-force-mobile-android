package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData

class TipDesarrollo(
    val personaId: Long,
    val region: String,
    val zona: String,
    val seccion: String,
    var data: ArrayList<TipData> = arrayListOf()
)

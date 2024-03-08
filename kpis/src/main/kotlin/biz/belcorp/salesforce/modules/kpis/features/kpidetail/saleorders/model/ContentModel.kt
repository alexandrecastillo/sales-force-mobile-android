package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

data class ContentModel(
    val type: Int,
    val items: List<ContentBaseModel>,
    val tip: String = EMPTY_STRING
)




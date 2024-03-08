package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

data class CollectionOrderRange(
    val range: String,
    val collected: Int = NUMBER_ZERO,
    val total: Int = NUMBER_ZERO,
    val position: Int = NUMBER_ZERO
)

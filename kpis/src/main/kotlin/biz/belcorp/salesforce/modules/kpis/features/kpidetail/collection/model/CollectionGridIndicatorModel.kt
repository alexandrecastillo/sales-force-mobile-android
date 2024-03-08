package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

import biz.belcorp.salesforce.core.constants.Constant

class CollectionGridIndicatorModel(
    val uaLabel: String,
    val recoveryValue: String = Constant.HYPHEN,
    val invoicedValue: String = Constant.HYPHEN,
    val collectedValue: String = Constant.HYPHEN
)

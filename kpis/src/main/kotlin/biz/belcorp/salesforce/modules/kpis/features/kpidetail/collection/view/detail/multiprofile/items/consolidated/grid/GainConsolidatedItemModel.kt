package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid

import biz.belcorp.salesforce.core.constants.Constant

class GainConsolidatedItemModel(
    val recoveryValue: String = Constant.EMPTY_STRING,
    val invoicedValue: String = Constant.EMPTY_STRING,
    val collectedValue: String = Constant.EMPTY_STRING
)

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.ProgressBarGridModel

data class GainHeaderModel(
    val recoveryAdvanceTitle: String = Constant.EMPTY_STRING,
    val recoveryTitle: String = Constant.EMPTY_STRING,
    val recoveryValue: String = Constant.EMPTY_STRING,
    val invoicedTitle: String = Constant.EMPTY_STRING,
    val invoicedValue: String = Constant.EMPTY_STRING,
    val collectedTitle: String = Constant.EMPTY_STRING,
    val collectedValue: String = Constant.EMPTY_STRING,
    val chargedOrderTitle: String = Constant.EMPTY_STRING,
    val chargedOrderList: List<ProgressBarGridModel> = arrayListOf(),
    val syncDate: String = Constant.EMPTY_STRING
)

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.model

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.ProgressModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentBaseModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.ProgressBarGridModel

data class GainDetailSeModel(
    val recoveryAdvanceTitle: String = Constant.EMPTY_STRING,
    val profitReceivedTitle: String = Constant.EMPTY_STRING,
    val chargedOrderTitle: String = Constant.EMPTY_STRING,
    val recoveryAdvanceList: List<ContentBaseModel> = arrayListOf(),
    val chargedOrderList: List<ProgressBarGridModel> = arrayListOf(),
    val profitReceivedList: List<CoupledModel> = arrayListOf(),
    val tooltip: String = Constant.EMPTY_STRING,
    val syncDate: String = Constant.EMPTY_STRING
)

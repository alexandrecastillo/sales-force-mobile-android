package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model

import biz.belcorp.salesforce.core.constants.Constant

class NewCycleGridModel(
    val segmentList: List<ProgressModel> = arrayListOf(),
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING
)

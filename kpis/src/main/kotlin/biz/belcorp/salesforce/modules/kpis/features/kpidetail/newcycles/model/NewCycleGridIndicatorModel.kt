package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model

import biz.belcorp.salesforce.core.constants.Constant

data class NewCycleGridIndicatorModel(
    var campaign: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val lowValue6d6: String = Constant.EMPTY_STRING,
    val lowValue5d5: String = Constant.EMPTY_STRING,
    val lowValue4d4: String = Constant.EMPTY_STRING,
    val lowValue3d3: String = Constant.EMPTY_STRING,
    val lowValue2d2: String = Constant.EMPTY_STRING,
    val highValue6d6: String = Constant.EMPTY_STRING,
    val highValue5d5: String = Constant.EMPTY_STRING,
    val highValue4d4: String = Constant.EMPTY_STRING
)

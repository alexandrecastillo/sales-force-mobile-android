package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel

data class NewCycleIndicatorModel(
    val segmentFeedListModel: List<SegmentFeedModel> = arrayListOf(),
    val lowValueSegments: List<ProgressModel> = arrayListOf(),
    val highValueSegments: List<ProgressModel> = arrayListOf(),
    val retentionResults: List<CoupledModel> = arrayListOf(),
    val title: String = Constant.EMPTY_STRING,
    val summary: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING,
    val retentionResultsTitle: String = Constant.EMPTY_STRING,
    val isBilling: Boolean = false,
    val hasWidthFitGrid: Boolean = false
)

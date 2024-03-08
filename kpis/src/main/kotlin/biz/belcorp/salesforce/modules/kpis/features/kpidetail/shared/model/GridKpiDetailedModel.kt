package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model

abstract class GridModel(val label: String, val value: String)

class DefaultGridModel(label: String, value: String) : GridModel(label, value)

class LeftAlignedGridModel(label: String, value: String) : GridModel(label, value)

class LeftAlignedWithDetailsGridModel(label: String, value: String, val details: String) : GridModel(label, value)

class LeftAlignedWithDetailsExpandedGridModel(label: String, value: String, val detailsFirstRow: String, val detailsFirstRowValue: String, val detailsSecondRow: String, val detailsSecondRowValue: String  ) : GridModel(label, value)

class ProgressBarGridModel(label: String, value: String, val currentProgress: Int, val maxProgress: Int) : GridModel(label, value)

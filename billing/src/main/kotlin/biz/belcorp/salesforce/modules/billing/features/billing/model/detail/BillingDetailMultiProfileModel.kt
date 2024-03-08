package biz.belcorp.salesforce.modules.billing.features.billing.model.detail

import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar

data class BillingDetailMultiProfileModel(
    val pendingPegs: IndicatorGoalBar.Model,
    val pendingNewCycle: List<BillingNewCycleModel>,
    val newCycleTitle: String
)

package biz.belcorp.salesforce.modules.billing.features.billing.model.detail

data class BillingNewCycleModel(
    val title: String,
    val description: String,
    val progress: Int,
    val maxProgress: Int,
    val progressLabel: String,
    val order: Int
)

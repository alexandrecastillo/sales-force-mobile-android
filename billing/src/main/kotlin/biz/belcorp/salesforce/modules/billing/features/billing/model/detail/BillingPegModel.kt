package biz.belcorp.salesforce.modules.billing.features.billing.model.detail

data class BillingPegModel(
    val title: String,
    val summary: String,
    val progress: Int,
    val maxProgress: Int,
    val description: String,
    val message: String
)

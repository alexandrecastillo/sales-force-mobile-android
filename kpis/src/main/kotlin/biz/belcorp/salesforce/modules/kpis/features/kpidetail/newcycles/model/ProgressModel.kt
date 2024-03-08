package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model

data class ProgressModel(
    val title: String,
    val summary: String?,
    val progress: Int? = null,
    val maxProgress: Int? = null
)

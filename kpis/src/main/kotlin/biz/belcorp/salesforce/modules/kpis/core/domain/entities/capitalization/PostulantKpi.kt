package biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization

class PostulantKpi(
    val currentCampaign: String,
    val preApproved: Int,
    val approved: Int,
    val inEvaluation: Int,
    val anticipatedEntries: Int
)

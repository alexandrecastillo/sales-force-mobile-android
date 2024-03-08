package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.billing

open class ConsolidatedCapitalizationKpiViewState {
    class Success(val campaign: String): ConsolidatedCapitalizationKpiViewState()
    class Failed(val message: String): ConsolidatedCapitalizationKpiViewState()
}

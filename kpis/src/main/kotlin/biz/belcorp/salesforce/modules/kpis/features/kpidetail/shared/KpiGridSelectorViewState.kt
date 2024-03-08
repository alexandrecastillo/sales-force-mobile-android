package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

open class KpiGridSelectorViewState {

    class Success(val label: String) : KpiGridSelectorViewState()

    class Failed(val message: String) : KpiGridSelectorViewState()

}

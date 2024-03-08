package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel

open class CapitalizationKpiOnBillingViewState {
    class Success(val data: List<CoupledModel>) : CapitalizationKpiOnBillingViewState()
    class Failed(val message: String) : CapitalizationKpiOnBillingViewState()
    class showProactive(val flagShowProactive: Boolean, val userCountryCode: String) : CapitalizationKpiOnBillingViewState()
}

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.CapitalizationKpiOnSalesModel

open class CapitalizationKpiDetailSeOnSalesViewState {
    class Success(val data: CapitalizationKpiOnSalesModel) :
        CapitalizationKpiDetailSeOnSalesViewState()
    class Failed(val message: String) : CapitalizationKpiDetailSeOnSalesViewState()
}

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters

sealed class KpiDetailViewState {
    class Success(var kpiDetailParams: KpiFragmentParameters) : KpiDetailViewState()
    class Error(val message: String) : KpiDetailViewState()
}

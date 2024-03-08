package biz.belcorp.salesforce.modules.kpis.features.kpis

import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboard

sealed class KpiDashboardViewState {
    class Success(val model: KpiDashboard) : KpiDashboardViewState()
    class Failure(val message: String) : KpiDashboardViewState()
}

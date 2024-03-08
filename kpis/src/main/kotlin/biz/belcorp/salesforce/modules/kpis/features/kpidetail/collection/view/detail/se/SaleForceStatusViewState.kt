package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se

import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus

sealed class SaleForceStatusViewState {
    class Success(val model: SaleForceStatus): SaleForceStatusViewState()
    class EmptyView(val emptySaleForce: String) : SaleForceStatusViewState()
    class Failed(val message: String) : SaleForceStatusViewState()
}

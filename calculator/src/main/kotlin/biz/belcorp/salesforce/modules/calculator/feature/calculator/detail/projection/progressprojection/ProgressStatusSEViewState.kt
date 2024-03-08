package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection

import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus


sealed class ProgressStatusSEViewState {
    class Success(val SaleForceStatus: SaleForceStatus) :
        ProgressStatusSEViewState()

    class Failed(val message: String) : ProgressStatusSEViewState()
}

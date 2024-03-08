package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection

import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus

sealed class StatusSEViewState {

    class Success(val SaleForceStatus: SaleForceStatus) :
        StatusSEViewState()

    class Failed(val message: String) : StatusSEViewState()
}

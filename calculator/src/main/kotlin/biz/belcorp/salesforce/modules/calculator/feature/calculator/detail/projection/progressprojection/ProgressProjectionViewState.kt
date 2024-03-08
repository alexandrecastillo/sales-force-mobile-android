package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection

import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionModel

sealed class ProgressProjectionViewState {

    class Success(val campaignProjectionModel: CampaignProjectionModel) :
        ProgressProjectionViewState()

    class Failed(val message: String) : ProgressProjectionViewState()
}

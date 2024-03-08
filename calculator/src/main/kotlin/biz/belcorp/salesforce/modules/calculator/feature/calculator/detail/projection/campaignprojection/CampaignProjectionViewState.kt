package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection

import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionModel

sealed class CampaignProjectionViewState {
    class Success(val campaignProjectionModel: CampaignProjectionModel) :
        CampaignProjectionViewState()

    class Failed(val message: String) : CampaignProjectionViewState()
}

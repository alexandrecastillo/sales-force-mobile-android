package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultsOptional

sealed class ResultsLastCampaignViewState{
    class Success(val model: ResultsOptional) : ResultsLastCampaignViewState()
    object Failure : ResultsLastCampaignViewState()
}

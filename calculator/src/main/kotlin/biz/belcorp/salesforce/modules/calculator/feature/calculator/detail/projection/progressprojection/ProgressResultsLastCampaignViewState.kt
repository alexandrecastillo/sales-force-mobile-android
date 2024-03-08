package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultsOptional

sealed class ProgressResultsLastCampaignViewState {
    class Success(val model: ResultsOptional) : ProgressResultsLastCampaignViewState()
    object Failure : ProgressResultsLastCampaignViewState()
}

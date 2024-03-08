package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.viewmodel

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.model.ResultModel

sealed class ResultsLastCampaignViewState {
    class Success(val model: ResultModel) : ResultsLastCampaignViewState()
    object Failure : ResultsLastCampaignViewState()
}
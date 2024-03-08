package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers

import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.resultado.ResultsOptional
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.model.ResultModel

class ResultLastCampaignMapper(
    private val textResolver: CoreTextResolver,
    private val salesMapper: ResultLastCampaignGoalRealMapper,
    private val resultByLastCampaignMapper: ResultByLastCampaignMapper
) {
    fun map(results: ResultsOptional): ResultModel {
        val campaign = results.result?.campaign.orEmpty().takeLastTwo()
        val salesGoal = salesMapper.map(results.result)
        val resultsLastCampaign = resultByLastCampaignMapper.map(results.result)
        return ResultModel(textResolver.getCampaign(campaign), salesGoal, resultsLastCampaign)
    }
}

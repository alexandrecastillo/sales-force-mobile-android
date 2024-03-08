package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultsOptional
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable.ResultsLastCampaignRepository

class GetResultsLastCampaignUseCase(
    private val resultsLastCampaignRepository: ResultsLastCampaignRepository,
) {

    suspend fun getResults(section: String? = null): ResultsOptional {

        val uaKey = LlaveUA(
            UserProperties.session?.region,
            UserProperties.session?.zona,
            section ?: UserProperties.session?.seccion
        )

        return resultsLastCampaignRepository.getResults(
            uaKey,
            UserProperties.session?.campaign?.codigo!!
        )
    }

}

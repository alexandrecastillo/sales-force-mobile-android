package biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultsOptional

interface ResultsLastCampaignRepository {
    suspend fun getResults(uaKey: LlaveUA, campaign: String): ResultsOptional
}

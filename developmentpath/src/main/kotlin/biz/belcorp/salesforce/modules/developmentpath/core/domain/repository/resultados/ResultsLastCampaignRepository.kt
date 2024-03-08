package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.resultado.ResultsOptional

interface ResultsLastCampaignRepository {
    suspend fun getResults(uaKey: LlaveUA, campaign: String): ResultsOptional
}

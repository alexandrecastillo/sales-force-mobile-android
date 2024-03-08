package biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.calculator.core.data.datasource.ResultsLastCampaignDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.mappers.ResultsLastCampaignMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultsOptional
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable.ResultsLastCampaignRepository

class ResultsLastCampaignDataRepository(
    private val resultsDataStore: ResultsLastCampaignDataStore,
    private val resultsMapper: ResultsLastCampaignMapper

) : ResultsLastCampaignRepository {

    override suspend fun getResults(uaKey: LlaveUA, campaign: String): ResultsOptional {
        val results = resultsDataStore.getResults(uaKey, campaign)
        return resultsMapper.map(results)
    }
}

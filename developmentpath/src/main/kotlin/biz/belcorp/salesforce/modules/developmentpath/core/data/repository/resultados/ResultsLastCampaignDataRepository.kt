package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.resultados

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.resultados.ResultsLastCampaignDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.resultados.ResultsLastCampaignMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.resultado.ResultsOptional
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultsLastCampaignRepository

class ResultsLastCampaignDataRepository(
    private val resultsDataStore: ResultsLastCampaignDataStore,
    private val resultsMapper: ResultsLastCampaignMapper

) : ResultsLastCampaignRepository {

    override suspend fun getResults(uaKey: LlaveUA, campaign: String): ResultsOptional {
        val results = resultsDataStore.getResults(uaKey, campaign)
        return resultsMapper.map(results)
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.resultado.ResultsOptional
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultsLastCampaignRepository

class GetResultsLastCampaignUseCase(
    private val resultsLastCampaignRepository: ResultsLastCampaignRepository,
    private val businessPartnersRepository: BusinessPartnerRepository,
    private val campaignsRepository: CampaniasRepository
) {

    suspend fun getResults(personIdentifier: PersonIdentifier): ResultsOptional {
        val person = getBusinessPartner(personIdentifier.code)
        val campaign = getCampaign(person.uaKey)
        return resultsLastCampaignRepository.getResults(person.uaKey, campaign.codigo)
    }

    private suspend fun getBusinessPartner(consultantCode: String): BusinessPartner {
        return businessPartnersRepository.getBusinessPartner(consultantCode) as BusinessPartner
    }

    private fun getCampaign(uaKey: LlaveUA): Campania {
        return requireNotNull(campaignsRepository.obtenerCampaniaInmediataAnterior(uaKey))
    }
}

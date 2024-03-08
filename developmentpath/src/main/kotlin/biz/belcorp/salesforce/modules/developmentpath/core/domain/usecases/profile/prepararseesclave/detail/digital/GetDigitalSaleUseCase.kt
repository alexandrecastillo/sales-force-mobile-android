package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.digital

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleContainer
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DigitalSaleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class GetDigitalSaleUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val digitalSaleSaleRepository: DigitalSaleRepository,
    private val configurationRepository: ConfigurationRepository,
    private val personRepository: RddPersonaRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getDigitalSaleByRole(personIdentifier: PersonIdentifier): DigitalSaleContainer {
        val configuration = configurationRepository.getConfiguration()
        val personRdd = getPersonRdd(personIdentifier)
        val digitalSaleList = digitalSaleSaleRepository.getDigitalSaleByRole(
            personIdentifier, personRdd.llaveUA, getCampaigns()
        )
        return DigitalSaleContainer(
            onlineStoreTitle = configuration.onlineStoreTitle,
            isOnlineStoreEnabled = configuration.isOnlineStoreEnabled,
            isGanaMas = configuration.isGanaMas,
            mainBrandType = configuration.mainBrand,
            digitalSaleList = digitalSaleList.sortedBy { it.campaign },
            role = personIdentifier.role
        )
    }

    private fun getPersonRdd(personIdentifier: PersonIdentifier) =
        requireNotNull(
            personRepository.recuperarPersonaPorId(personIdentifier.id, personIdentifier.role)
        )

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
            .map { it.codigo }
            .sorted()
            .takeLast(NUMBER_THREE)
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.utils.isNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp.DreamBpRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.utils.Constants

class GetBusinessPartnerDreamUseCase(
    private val dreamBpRepository: DreamBpRepository,
    private val personRepository: RddPersonaRepository,
    private val configurationRepository: ConfigurationRepository
) {

    suspend fun getBusinessPartnerDream(personIdentifier: PersonIdentifier): DreamBusinessPartner? {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        val uaKey = requireNotNull(person?.llaveUA)
        val configuration = configurationRepository.getConfiguration(uaKey)
        val businessPartnerDreams =
            dreamBpRepository.getBusinessPartnerDream(person?.personCode, uaKey)
        val dreamBusinessPartner: DreamBusinessPartner?

        val dreamByStatusActive =
            businessPartnerDreams.firstOrNull { dream: DreamBusinessPartner ->
                Constants.ACTIVE == dream.status
            }

        dreamBusinessPartner = if (dreamByStatusActive.isNull()) {
            val dreamsCompleted =
                businessPartnerDreams.filter { dream: DreamBusinessPartner ->
                    Constants.COMPLETED ==
                        dream.status
                }
            dreamsCompleted.sortedByDescending { dream: DreamBusinessPartner -> dream.dateCreated }
                .firstOrNull()

        } else {
            dreamByStatusActive
        }
        return if (dreamBusinessPartner.isNull()) {
            DreamBusinessPartner(currencySymbol = configuration.currencySymbol)
        } else {
            dreamBusinessPartner?.bpName = person?.primerNombre
            dreamBusinessPartner?.currencySymbol = configuration.currencySymbol
            dreamBusinessPartner
        }
    }
}

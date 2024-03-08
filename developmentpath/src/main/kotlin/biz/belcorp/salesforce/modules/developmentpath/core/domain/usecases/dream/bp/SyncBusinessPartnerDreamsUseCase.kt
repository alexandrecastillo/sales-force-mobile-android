package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp.DreamBpRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class SyncBusinessPartnerDreamsUseCase(
    private val dreamBpRepository: DreamBpRepository,
    private val personRepository: RddPersonaRepository
) {

    suspend fun syncBusinessPartnerDreams(personIdentifier: PersonIdentifier) {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        val uaKey = requireNotNull(person?.llaveUA)
        dreamBpRepository.syncBusinessPartnerDreams(
            uaKey,
            country = UserProperties.session?.countryIso!!
        )
    }
}

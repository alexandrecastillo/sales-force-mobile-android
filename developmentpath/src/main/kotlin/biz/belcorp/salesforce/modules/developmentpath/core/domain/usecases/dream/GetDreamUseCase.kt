package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties.session
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.utils.isNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream.DreamRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.utils.Constants

class GetDreamUseCase(
    private val dreamRepository: DreamRepository,
    private val personRepository: RddPersonaRepository,
    private val configurationRepository: ConfigurationRepository,
) {

    suspend fun getDream(personIdentifier: PersonIdentifier): Dream? {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        val uaKey = requireNotNull(person?.llaveUA)
        val configuration = configurationRepository.getConfiguration(uaKey)
        val dreams = dreamRepository.getDreams(person?.personCode, uaKey)
        val dream: Dream?

        val dreamByStatusActive = dreams.firstOrNull { dream: Dream ->
            Constants.ACTIVE == dream.status
        }

        dream = if (dreamByStatusActive.isNull()) {
            val dreamsCompleted = dreams.filter { dream: Dream ->
                Constants.COMPLETED ==
                    dream.status
            }
            dreamsCompleted.sortedByDescending { dream: Dream -> dream.dateCreated }
                .firstOrNull()

        } else {
            dreamByStatusActive
        }
        return if (dream.isNull()) {
            Dream(currencySymbol = configuration.currencySymbol)
        } else {
            dream?.consultantName = person?.primerNombre
            dream?.currencySymbol = configuration.currencySymbol
            dream
        }
    }

    suspend fun syncDreams(personIdentifier: PersonIdentifier) {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        val uaKey = requireNotNull(person?.llaveUA)
        dreamRepository.syncDreams(
            uaKey,
            country = session?.countryIso!!
        )
    }

    suspend fun createDream(uaKey: LlaveUA, dream: EditCreateDream) {
        dreamRepository.createDreams(uaKey, country = session?.countryIso!!, dream = dream)
    }

    suspend fun editDream(uaKey: LlaveUA, dream: EditCreateDream) {
        dreamRepository.editDreams(
            uaKey, country = session?.countryIso!!,
            dream = dream, campaign = session?.campaign!!.codigo
        )
    }
}

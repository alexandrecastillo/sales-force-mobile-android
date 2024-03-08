package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.datos

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class ObtenerNombrePersonaUseCase(
    private val rddPersonaRepository: RddPersonaRepository
) {

    fun get(personIdentifier: PersonIdentifier): PersonaRdd {
        val person = rddPersonaRepository
            .recuperarPersonaPorId(personIdentifier.id, personIdentifier.role)
        return requireNotNull(person)
    }

}

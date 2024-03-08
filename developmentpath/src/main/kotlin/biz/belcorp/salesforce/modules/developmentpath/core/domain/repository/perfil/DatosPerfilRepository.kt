package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd

interface DatosPerfilRepository {
    suspend fun sync(person: PersonaRdd)
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.CampaniaCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd

interface CampaniaACampaniaRepository {
    fun obtener(persona: PersonaRdd, campanias: List<Campania>): List<CampaniaCampania>
}

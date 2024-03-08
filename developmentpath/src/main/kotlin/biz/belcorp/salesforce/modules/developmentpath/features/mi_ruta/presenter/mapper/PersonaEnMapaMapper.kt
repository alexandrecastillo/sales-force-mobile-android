package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaModel

class PersonaEnMapaMapper {

    fun map(personas: List<PersonaRdd>) = personas.map { map(it) }

    fun map(persona: PersonaRdd): PersonaEnMapaModel {
        with(persona) {
            return PersonaEnMapaModel(
                personaId = id,
                personCode = persona.personCode,
                iniciales = iniciales,
                ubicacion = ubicacion,
                tieneAlgunaVisitaRegistrada = agenda.tieneAlMenosUnaRegistrada,
                rol = rol
            )
        }
    }
}

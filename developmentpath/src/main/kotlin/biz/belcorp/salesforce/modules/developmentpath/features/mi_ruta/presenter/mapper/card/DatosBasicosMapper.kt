package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card

import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel


class DatosBasicosMapper {
    fun map(persona: PersonaRdd): MiRutaCardModel.DatosBasicos {
        return MiRutaCardModel.DatosBasicos(
                personaId = persona.id,
                personCode = persona.personCode,
                nombre = WordUtils.capitalizeFully(persona.primerNombre ?: ""),
                nombreApellido = WordUtils.capitalizeFully(persona.nombreApellido),
                iniciales = persona.iniciales.toUpperCase(),
                url = "")
    }
}

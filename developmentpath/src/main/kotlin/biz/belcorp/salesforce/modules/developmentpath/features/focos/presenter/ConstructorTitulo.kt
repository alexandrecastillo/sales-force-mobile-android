package biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter

import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd

class ConstructorTitulo {

    fun crear(persona: PersonaRdd): String {
        return "${ConstructorZonificacion.construir(
                persona)} - ${obtenerDatosPersona(persona)}"
    }

    private fun obtenerDatosPersona(persona: PersonaRdd): String {
        return WordUtils.capitalizeFully("${persona.primerNombre} " +
                (persona.primerApellido ?: ""))
    }
}

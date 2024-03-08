package biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd

interface ConstructorZonificacion {

    companion object {
        fun construir(persona: PersonaRdd): String {
            return when (persona) {
                is GerenteRegionRdd -> "Región ${persona.region.codigo}"
                is GerenteZonaRdd -> "Zona ${persona.zona.codigo}"
                is SociaEmpresariaRdd -> "SE (Sección ${persona.seccion.codigo})"
                else -> throw Exception("Rol no soportado")
            }
        }
    }
}

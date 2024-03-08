package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd

class BuscadorPersonasCerca(
    private val universo: List<PersonaRdd>,
    val origen: Pair<Double, Double>,
    private val radioBusqueda: Int
) {
    fun buscarEnRadio(): List<PersonaRdd> {
        return universo.filter {
            val distancia = it.ubicacion.distanciaA(origen)
            distancia != null && distancia <= radioBusqueda
        }
    }
}

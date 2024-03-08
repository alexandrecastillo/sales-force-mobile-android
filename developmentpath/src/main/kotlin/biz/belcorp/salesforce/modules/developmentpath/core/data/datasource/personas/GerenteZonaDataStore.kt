package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd

interface GerenteZonaDataStore {
    fun obtenerPlanificables(planId: Long): List<GerenteZonaRdd>
    fun recuperarPorId(personaId: Long): GerenteZonaRdd?
    fun recuperarIdSegunUsuario(usuario: String): Long?
}

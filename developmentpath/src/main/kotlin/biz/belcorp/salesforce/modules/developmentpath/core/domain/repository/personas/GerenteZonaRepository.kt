package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd

interface GerenteZonaRepository {
    fun obtener(planId: Long): List<GerenteZonaRdd>
}

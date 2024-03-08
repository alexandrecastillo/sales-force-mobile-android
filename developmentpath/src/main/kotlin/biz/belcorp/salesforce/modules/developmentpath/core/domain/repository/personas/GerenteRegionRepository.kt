package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd

interface GerenteRegionRepository {
    fun obtener(planId: Long): List<GerenteRegionRdd>
}

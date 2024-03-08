package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd

interface PosibleConsultoraRepository {
    fun obtener(planId: Long): List<PosibleConsultoraRdd>
}

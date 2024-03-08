package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd

interface SociaEmpresariaRepository {
    fun obtener(planId: Long): List<SociaEmpresariaRdd>
}

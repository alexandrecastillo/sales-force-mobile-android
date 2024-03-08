package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultantDevelopmentPath
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd

interface ConsultoraRDDRepository {
    fun obtener(planId: Long): List<ConsultoraRdd>
    fun getConsultants(planId: Long): List<ConsultantDevelopmentPath>
}

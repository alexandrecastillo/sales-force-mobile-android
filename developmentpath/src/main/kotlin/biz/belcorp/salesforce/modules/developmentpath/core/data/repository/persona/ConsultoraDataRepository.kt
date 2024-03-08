package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas.ConsultoraDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultantDevelopmentPath
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.ConsultoraRDDRepository

class ConsultoraDataRepository(private val dbStore: ConsultoraDBDataStore) :
    ConsultoraRDDRepository {

    override fun obtener(planId: Long): List<ConsultoraRdd> {
        return dbStore.obtener(planId)
    }

    override fun getConsultants(planId: Long): List<ConsultantDevelopmentPath> {
        return dbStore.getConsultants(planId)
    }
}

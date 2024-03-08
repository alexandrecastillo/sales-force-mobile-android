package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas.SociaEmpresariaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SociaEmpresariaRepository

class SociaEmpresariaDataRepository(private val sociaDataStore: SociaEmpresariaDBDataStore)
    : SociaEmpresariaRepository {

    override fun obtener(planId: Long): List<SociaEmpresariaRdd> {
        return sociaDataStore.obtener(planId)
    }
}

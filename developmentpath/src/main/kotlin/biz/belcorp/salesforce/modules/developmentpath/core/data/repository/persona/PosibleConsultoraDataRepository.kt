package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas.PostulanteDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.PosibleConsultoraRepository
import io.reactivex.Single

class PosibleConsultoraDataRepository(private val dbStore: PostulanteDBDataStore) :
    PosibleConsultoraRepository {

    override fun obtener(planId: Long): List<PosibleConsultoraRdd> {
        return dbStore.obtener(planId)
    }

}

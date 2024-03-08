package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa.SeccionDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddSeccionRepository

class RddSeccionDataRepository(private val dbStore: SeccionDBDataStore) : RddSeccionRepository {
    override fun recuperarParaAvance(codigoZona: String): List<SeccionRdd> {
        return dbStore.recuperarParaAvance(codigoZona)
    }
}

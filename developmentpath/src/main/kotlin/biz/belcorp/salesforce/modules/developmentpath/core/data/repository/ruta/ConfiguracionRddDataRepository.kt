package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.ConfiguracionRutaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.parametros.ParametrosRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository

class ConfiguracionRddDataRepository(private val dbStore: ConfiguracionRutaDBDataStore) :
    ConfigRddRepository {
    override fun get(planId: Long): ParametrosRdd? {
        return dbStore.get(planId)
    }
}

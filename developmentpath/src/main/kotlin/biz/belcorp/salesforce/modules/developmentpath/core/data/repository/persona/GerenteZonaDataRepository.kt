package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas.GerenteZonaDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.GerenteZonaRepository

class GerenteZonaDataRepository(private val dbStore: GerenteZonaDataStore) : GerenteZonaRepository {

    override fun obtener(planId: Long): List<GerenteZonaRdd> {
        return dbStore.obtenerPlanificables(planId)
    }
}

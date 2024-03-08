package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.hitos

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.hito.HitoDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Hito
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.HitoEnRegion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.hito.HitoRepository

class HitoDataRepository(private val dbStore: HitoDBDataStore) : HitoRepository {
    override fun obtenerPorRegion(): List<HitoEnRegion> {
        return dbStore.obtenerPorRegion()
    }

    override fun obtenerPorZona(codigoZona: String): List<Hito> {
        return dbStore.obtenerPorZona(codigoZona)
    }
}

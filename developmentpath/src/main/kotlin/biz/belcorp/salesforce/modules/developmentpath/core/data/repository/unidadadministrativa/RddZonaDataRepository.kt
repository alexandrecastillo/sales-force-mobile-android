package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa.ZonaDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.ZonaGzMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddZonaRepository

class RddZonaDataRepository(
    private val dbDataStore: ZonaDataStore,
    private val avanceMapper: ZonaGzMapper
) : RddZonaRepository {

    override fun recuperarParaAvance(codigoRegion: String): List<ZonaRdd> {
        return avanceMapper.parse(dbDataStore.recuperarAvances(codigoRegion))
    }
}

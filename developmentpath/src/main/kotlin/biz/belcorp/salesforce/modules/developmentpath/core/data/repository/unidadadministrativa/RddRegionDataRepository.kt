package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa.RegionDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.RegionGrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddRegionRepository
import io.reactivex.Single

class RddRegionDataRepository(private val localStore: RegionDBDataStore,
                              private val mapper: RegionGrMapper
) : RddRegionRepository {

    override fun recuperarParaAvance(): Single<List<RegionRdd>> {
        return localStore.recuperarAvance().map { mapper.parse(it) }
    }
}

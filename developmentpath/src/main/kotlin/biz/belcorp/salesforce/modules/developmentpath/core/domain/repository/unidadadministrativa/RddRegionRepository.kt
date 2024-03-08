package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import io.reactivex.Single

interface RddRegionRepository {
    fun recuperarParaAvance(): Single<List<RegionRdd>>
}

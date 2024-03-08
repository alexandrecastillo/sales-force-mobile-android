package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsGR
import io.reactivex.Single

interface GrRDDIndicatorRepository {
    fun getItsRDDIndicatorDataForGr(): Single<IndicadorRddArgsGR>
    fun getGrRDDIndicatorDataForGr(uaSegmentId: String): Single<IndicadorRddArgsGR>
    fun getGrRDDIndicatorDataForGrAsDv(uaSegmentId: String): Single<IndicadorRddArgsGR>
}

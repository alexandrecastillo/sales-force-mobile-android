package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsGZ
import io.reactivex.Single

interface GzRDDIndicatorRepository {
    fun getGzRddIndicatorData(): Single<IndicadorRddArgsGZ>
    fun getGzRddIndicatorDataBySegment(uaSegmentId: String): Single<IndicadorRddArgsGZ>
}

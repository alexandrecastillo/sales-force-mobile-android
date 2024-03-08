package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsSE
import io.reactivex.Single

interface SeRDDIndicatorRepository {
    fun getSeRddIndicatorData(uaSegmentId: String): Single<IndicadorRddArgsSE>
}

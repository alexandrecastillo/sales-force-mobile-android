package biz.belcorp.salesforce.modules.inspires.core.domain.repository.indicator

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import io.reactivex.Single

interface IndicatorRepository {
    fun one(): Single<InspiraIndicador>
}

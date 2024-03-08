package biz.belcorp.salesforce.modules.inspires.core.domain.repository.progressperiod

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvancesPeriodo
import io.reactivex.Single

interface ProgressPeriodRepository {
    fun all(): Single<List<InspiraAvancesPeriodo>>

    fun has(): Single<Boolean>
}

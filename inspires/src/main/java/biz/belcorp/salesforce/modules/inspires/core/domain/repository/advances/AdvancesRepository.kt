package biz.belcorp.salesforce.modules.inspires.core.domain.repository.advances

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvances
import io.reactivex.Single

interface AdvancesRepository {
    fun all(): Single<List<InspiraAvances>>
}

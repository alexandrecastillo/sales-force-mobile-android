package biz.belcorp.salesforce.modules.inspires.core.domain.repository.legendconditions

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyenda
import io.reactivex.Single

interface LegendConditionsRepository {
    fun all(): Single<List<InspiraCondicionesLeyenda>>
}

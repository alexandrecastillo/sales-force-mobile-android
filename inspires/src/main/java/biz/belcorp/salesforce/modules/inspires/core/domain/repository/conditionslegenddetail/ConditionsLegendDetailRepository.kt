package biz.belcorp.salesforce.modules.inspires.core.domain.repository.conditionslegenddetail

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyendaDetalle
import io.reactivex.Single

interface ConditionsLegendDetailRepository {
    fun all(): Single<List<InspiraCondicionesLeyendaDetalle>>
}

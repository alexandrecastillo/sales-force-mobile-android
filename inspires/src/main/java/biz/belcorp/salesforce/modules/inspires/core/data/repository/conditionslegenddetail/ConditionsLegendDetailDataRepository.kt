package biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail

import biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.data.ConditionsLegendDetailDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.mapper.ConditionsLegendDetailEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyendaDetalle
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.conditionslegenddetail.ConditionsLegendDetailRepository
import io.reactivex.Single

class ConditionsLegendDetailDataRepository (
    private val conditionsLegendDetailDBDataStore: ConditionsLegendDetailDBDataStore,
    private val conditionsLegendDetailEntityDataMapper: ConditionsLegendDetailEntityDataMapper
) : ConditionsLegendDetailRepository {

    override fun all(): Single<List<InspiraCondicionesLeyendaDetalle>> {
        return conditionsLegendDetailDBDataStore.all().map {
            conditionsLegendDetailEntityDataMapper.parseLevelParameterList(it)
        }
    }

}

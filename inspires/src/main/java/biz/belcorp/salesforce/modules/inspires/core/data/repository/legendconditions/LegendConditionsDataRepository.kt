package biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions

import biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.data.LegendConditionsDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.mapper.LegendConditionsEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyenda
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.legendconditions.LegendConditionsRepository
import io.reactivex.Single

class LegendConditionsDataRepository (
    private val legendConditionsDBDataStore: LegendConditionsDBDataStore,
    private val legendConditionsEntityDataMapper: LegendConditionsEntityDataMapper
) : LegendConditionsRepository {

    override fun all(): Single<List<InspiraCondicionesLeyenda>> {
        return legendConditionsDBDataStore.all().map {
            legendConditionsEntityDataMapper.parseLevelParameterList(it)
        }
    }

}

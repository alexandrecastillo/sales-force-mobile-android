package biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator

import biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.data.IndicatorDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.mapper.IndicatorEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.indicator.IndicatorRepository
import io.reactivex.Single

class IndicatorDataRepository (
    private val indicatorDBDataStore: IndicatorDBDataStore,
    private val indicatorEntityDataMapper: IndicatorEntityDataMapper
) : IndicatorRepository {

    override fun one(): Single<InspiraIndicador> {
        return indicatorDBDataStore.one().map {
            indicatorEntityDataMapper.parseLevelParameter(it)
        }
    }

}

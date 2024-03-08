package biz.belcorp.salesforce.modules.inspires.core.data.repository.advances

import biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.data.AdvancesDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.mapper.AdvancesEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvances
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.advances.AdvancesRepository
import io.reactivex.Single

class AdvancesDataRepository (
    private val advancesDBDataStore: AdvancesDBDataStore,
    private val advancesEntityDataMapper: AdvancesEntityDataMapper
) : AdvancesRepository {

    override fun all(): Single<List<InspiraAvances>> {
        return advancesDBDataStore.all().map {
            advancesEntityDataMapper.parseLevelParameterList(it)
        }
    }

}

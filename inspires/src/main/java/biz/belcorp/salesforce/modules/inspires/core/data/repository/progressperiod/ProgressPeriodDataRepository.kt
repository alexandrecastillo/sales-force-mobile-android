package biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod

import biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.data.ProgressPeriodDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.mapper.ProgressPeriodEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvancesPeriodo
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.progressperiod.ProgressPeriodRepository
import io.reactivex.Single

class ProgressPeriodDataRepository (
    private val progressPeriodDBDataStore: ProgressPeriodDBDataStore,
    private val progressPeriodEntityDataMapper: ProgressPeriodEntityDataMapper
) : ProgressPeriodRepository {

    override fun all(): Single<List<InspiraAvancesPeriodo>> {
        return progressPeriodDBDataStore.all().map {
            progressPeriodEntityDataMapper.parseLevelParameterList(it)
        }
    }

    override fun has(): Single<Boolean> {
        return progressPeriodDBDataStore.has()
    }

}

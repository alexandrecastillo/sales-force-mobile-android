package biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail

import biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.data.ContestDetailDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.mapper.ContestDetailEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ContestDetail
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.contestdetail.ContestDetailRepository
import io.reactivex.Single

class ContestDetailDataRepository (
    private val contestDetailDBDataStore: ContestDetailDBDataStore,
    private val contestDetailEntityDataMapper: ContestDetailEntityDataMapper) : ContestDetailRepository {

    override fun list(): Single<List<ContestDetail>> {
        return contestDetailDBDataStore.all().map {
            contestDetailEntityDataMapper.parseContestDetail(it)
        }
    }
}

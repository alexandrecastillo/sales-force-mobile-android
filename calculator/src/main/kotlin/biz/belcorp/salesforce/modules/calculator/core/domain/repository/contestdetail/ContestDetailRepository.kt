package biz.belcorp.salesforce.modules.calculator.core.domain.repository.contestdetail

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ContestDetail
import io.reactivex.Single

interface ContestDetailRepository {
    fun list(): Single<List<ContestDetail>>
}

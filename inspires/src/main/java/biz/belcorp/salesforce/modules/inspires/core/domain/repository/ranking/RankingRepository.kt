package biz.belcorp.salesforce.modules.inspires.core.domain.repository.ranking

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraRanking
import io.reactivex.Single

interface RankingRepository {
    fun all(): Single<List<InspiraRanking>>

    fun oneWhenIsUser(): Single<InspiraRanking>
}

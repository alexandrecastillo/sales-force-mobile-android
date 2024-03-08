package biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking

import biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.data.RankingDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.mapper.RankingEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraRanking
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.ranking.RankingRepository
import io.reactivex.Single

class RankingDataRepository (
    private val rankingDBDataStore: RankingDBDataStore,
    private val rankingEntityDataMapper: RankingEntityDataMapper
) : RankingRepository {

    override fun all(): Single<List<InspiraRanking>> {
        return rankingDBDataStore.all().map {
            rankingEntityDataMapper.parseLevelParameterList(it)
        }
    }

    override fun oneWhenIsUser(): Single<InspiraRanking> {
        return rankingDBDataStore.oneWhenIsUser().map {
            rankingEntityDataMapper.parseLevelParameter(it)
        }
    }

}

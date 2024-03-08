package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ranking

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ranking.RankingGraphic

interface ProfileRankingRepository {

    suspend fun syncRankingInfo(uaKey: LlaveUA, campaigns: List<String>, country: String)

    suspend fun getRankingInfo(uaKey: LlaveUA, campaigns: List<String>): List<RankingGraphic>

}

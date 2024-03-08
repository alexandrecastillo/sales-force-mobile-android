package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RankingDto(
    @SerialName(COLLECTION_KEY)
    val ranking: List<Ranking>
) {

    @Serializable
    class Ranking(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_PRODUCTIVITY_PERCENTAGE)
        val productivityPercentage: Double,
        @SerialName(KEY_UNIQUE_IP_PERCENTAGE)
        val uniqueIpPercentage: Double,
        @SerialName(KEY_RETENTION_6D6_PERCENTAGE)
        val retention6d6Percentage: Double,
        @SerialName(KEY_ACTIVES_RETENTION_GOAL_PERCENTAGE)
        val activesRetentionGoalPercentage: Double
    )

}

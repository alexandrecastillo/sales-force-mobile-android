package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class RankingQuery(private val request: RankingParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_PRODUCTIVITY_PERCENTAGE)
                field(KEY_UNIQUE_IP_PERCENTAGE)
                field(KEY_RETENTION_6D6_PERCENTAGE)
                field(KEY_ACTIVES_RETENTION_GOAL_PERCENTAGE)
            }
        }
    }

}

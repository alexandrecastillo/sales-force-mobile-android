package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class BonusQuery(val params: BonusParams) : BaseQuery() {

    override val keyFunctionName = BONUS_FUNCTION_NAME_KEY
    override val keyFilter = BONUS_FILTER_KEY
    override val keyFilterType = BONUS_FILTER_TYPE_KEY
    override val keyCollection = BONUS_COLLECTION_KEY
    override val keyInput = BONUS_INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(BONUS_CAMPAIGN_KEY)
                field(BONUS_REGION_KEY)
                field(BONUS_ZONE_KEY)
                field(BONUS_SECTION_KEY)
                field(BONUS_CODE_KEY)
                field(BONUS_UNIT_AMOUNT_KEY)
            }
        }
    }

}


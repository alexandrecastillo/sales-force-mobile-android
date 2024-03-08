package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.*


class DreamBpQuery(private val request: DreamBpParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get(): Kraph {

        val tempKraph = Kraph {
            query(keyFunctionName) {
                fieldObject(
                    keyCollection, mapOf(keyInput to getInput())
                ) {
                    field(KEY_DREAM_ID)
                    field(KEY_BP_CODE)
                    field(KEY_AMOUNT_TO_COMPLETE)
                    field(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
                    field(KEY_COMMENTS)
                    field(KEY_DREAM)
                    field(KEY_REGION)
                    field(KEY_SECTION)
                    field(KEY_ZONE)
                    field(KEY_CAMPAIGN_CREATED)
                    field(KEY_STATUS)
                    field(KEY_DATE_CREATED)
                    field(KEY_DATE_EDITED)
                    field(KEY_DATE_COMPLETED)
                    field(KEY_DATE_CAMPAIGN_END)
                    fieldObject(KEY_PROGRESS_BY_CAMPAIGNS) {
                        field(KEY_CONSULTANT_LIST) {
                            field(KEY_CAMPAIGN)
                            field(KEY_TOTAL)
                        }
                        field(KEY_TOTAL_GAIN)
                    }
                }
            }
        }

        return tempKraph
    }


}

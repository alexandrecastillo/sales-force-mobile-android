package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DreamCreateBpParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_BP_CODE)
    var code: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN_CREATED)
    var campaignCreated: String = Constant.EMPTY_STRING,
    @SerialName(KEY_DREAM)
    var dream: String = Constant.EMPTY_STRING,
    @SerialName(KEY_COMMENTS)
    var comments: String = Constant.EMPTY_STRING,
    @SerialName(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
    var campaignsToAchieve: Int = 0,
    @SerialName(KEY_AMOUNT_TO_COMPLETE)
    var amountToComplete: Long = 0
) {
    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(DreamCreateBpParams.serializer(), this)
    }
}

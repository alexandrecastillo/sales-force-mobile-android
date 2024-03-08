package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_AMOUNT_TO_COMPLETE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_CAMPAIGN_CREATED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_COMMENTS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_CONSULTANT_CODE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_COUNTRY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DREAM
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_NUMBER_CAMPAIGNS_TO_COMPLETE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_REGION
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_SECTION
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DreamCreateParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CONSULTANT_CODE)
    var consultantCode: String = Constant.EMPTY_STRING,
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
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

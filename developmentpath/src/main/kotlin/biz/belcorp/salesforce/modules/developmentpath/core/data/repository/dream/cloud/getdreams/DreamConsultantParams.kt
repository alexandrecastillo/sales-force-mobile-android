package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.getdreams

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class DreamConsultantParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CONSULTANT_CODE)
    var consultantCode: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN_CREATED)
    var campaignCreated: String = Constant.EMPTY_STRING
) {
    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

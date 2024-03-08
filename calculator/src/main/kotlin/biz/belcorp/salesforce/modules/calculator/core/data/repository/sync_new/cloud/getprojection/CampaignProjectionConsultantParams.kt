package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.getprojection

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CampaignProjectionConsultantParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN)
    var campaign: String = Constant.EMPTY_STRING,
    @SerialName(KEY_PHASE)
    var phase: String = Constant.EMPTY_STRING
) {
    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

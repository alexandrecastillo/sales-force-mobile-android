package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault


@Serializable
class DigitalParams(
    @SerialName(KEY_COUNTRY)
    var country: String = EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN)
    var campaign: String = EMPTY_STRING,
    @SerialName(KEY_REGION)
    var region: String = EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = EMPTY_STRING
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

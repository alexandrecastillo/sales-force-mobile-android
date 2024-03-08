package biz.belcorp.salesforce.core.data.repository.directory.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class DirectoryParams(
    @SerialName(KEY_CAMPAIGN)
    val campaign: String = EMPTY_STRING,
    @SerialName(KEY_COUNTRY)
    val country: String = EMPTY_STRING,
    @SerialName(KEY_REGION)
    val region: String = EMPTY_STRING,
    @SerialName(KEY_PROFILE)
    val profile:String = EMPTY_STRING
) {

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

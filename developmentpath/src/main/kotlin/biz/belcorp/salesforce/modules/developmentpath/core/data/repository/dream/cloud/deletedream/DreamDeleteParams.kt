package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.deletedream

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DreamDeleteParams(
    @SerialName(KEY_DREAM_ID)
    var id: String = Constant.EMPTY_STRING,
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
) {
    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

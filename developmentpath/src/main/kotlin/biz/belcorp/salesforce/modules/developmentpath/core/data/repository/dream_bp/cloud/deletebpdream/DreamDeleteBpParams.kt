package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.deletebpdream

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_COUNTRY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_DREAM_ID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DreamDeleteBpParams(
    @SerialName(KEY_DREAM_ID)
    var id: String = Constant.EMPTY_STRING,
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
) {
    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

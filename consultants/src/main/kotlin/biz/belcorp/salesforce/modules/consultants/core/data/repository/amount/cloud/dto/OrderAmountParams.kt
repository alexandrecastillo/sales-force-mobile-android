package biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.KEY_COUNTRY
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class OrderAmountParams {

    @SerialName(KEY_COUNTRY)
    var country: String = EMPTY_STRING
    @SerialName(KEY_REGION)
    var region: String = EMPTY_STRING
    @SerialName(KEY_ZONE)
    var zone: String = EMPTY_STRING
    @SerialName(KEY_SECTION)
    var section: String = EMPTY_STRING
    @SerialName(CAMPAIGN_KEY)
    var campaign: String = EMPTY_STRING

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }

}

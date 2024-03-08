package biz.belcorp.salesforce.modules.brightpath.core.data.network.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_COUNTRY
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.utils.JsonUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BusinessPartnerChangeLevelParams(
    @SerialName(KEY_COUNTRY)
    private val country: String,
    @SerialName(KEY_CONSULTANT_CODE)
    val consultantCode: String,
    @SerialName(KEY_CAMPAIGN)
    val campaigns: List<String>,
    @SerialName(KEY_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = Constant.EMPTY_STRING
) {

    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

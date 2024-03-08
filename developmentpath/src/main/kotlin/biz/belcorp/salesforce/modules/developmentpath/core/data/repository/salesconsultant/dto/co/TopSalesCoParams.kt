package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class TopSalesCoParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN)
    var campaigns: List<String> = emptyList(),
    @SerialName(KEY_CONSULTANT_CODE)
    var consultantCode: String = Constant.EMPTY_STRING,
    @SerialName(KEY_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = Constant.EMPTY_STRING
) {

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }

}

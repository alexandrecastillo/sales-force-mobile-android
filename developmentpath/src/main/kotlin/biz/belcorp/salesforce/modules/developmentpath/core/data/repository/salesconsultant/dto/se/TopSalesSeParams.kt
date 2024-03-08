package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class TopSalesSeParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    var section: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN)
    var campaign: List<String> = emptyList()
) {

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }

}

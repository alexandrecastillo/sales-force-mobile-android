package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SaleConsultantParams(
    @SerialName(KEY_COUNTRY)
    private val country: String,
    @SerialName(KEY_CONSULTANT_CODE)
    val consultantCode: String,
    @SerialName(KEY_CAMPAIGN)
    var campaigns: List<String>,
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

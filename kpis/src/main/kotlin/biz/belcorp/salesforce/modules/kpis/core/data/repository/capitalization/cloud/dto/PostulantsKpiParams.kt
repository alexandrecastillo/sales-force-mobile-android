package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class PostulantsKpiParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
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

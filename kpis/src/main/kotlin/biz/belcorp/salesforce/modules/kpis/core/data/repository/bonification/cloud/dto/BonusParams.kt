package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_COUNTRY
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
data class BonusParams(
    @SerialName(KEY_COUNTRY)
    var country: String,
    @SerialName(KEY_CAMPAIGN)
    var campaign: String,
    @SerialName(KEY_REGION)
    var region: String,
    @SerialName(KEY_ZONE)
    var zone: String,
    @SerialName(BONUS_TYPES_KEY)
    var types: List<String> = listOf(BONUS_TYPE_6D6AV, BONUS_TYPE_6D6BV)
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}


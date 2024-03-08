package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class CollectionRequestParams(
    @SerialName(KEY_COUNTRY)
    var country: String,
    @SerialName(KEY_CAMPAIGN)
    var campaign: List<String>,
    @SerialName(KEY_PROFILE)
    var profile: String,
    @SerialName(KEY_REGION)
    var region: String,
    @SerialName(KEY_ZONE)
    var zone: String,
    @SerialName(KEY_SECTION)
    var section: String,
    @SerialName(KEY_SHOW_REGIONS)
    var showRegions: Boolean = false,
    @SerialName(KEY_SHOW_ZONES)
    var showZones: Boolean = false,
    @SerialName(KEY_SHOW_SECTION)
    var showSection: Boolean = false,
    @SerialName(KEY_DAYS)
    var days: List<String>
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

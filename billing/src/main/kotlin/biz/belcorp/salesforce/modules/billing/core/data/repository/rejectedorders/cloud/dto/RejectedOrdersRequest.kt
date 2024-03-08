package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
data class RejectedOrdersRequest(
    @SerialName(KEY_COUNTRY)
    val country: String,
    @SerialName(CAMPAIGN_KEY)
    val campaign: String,
    @SerialName(KEY_REGION)
    val region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    val zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    val section: String = Constant.EMPTY_STRING,
    @SerialName(SHOW_REGIONS_KEY)
    val showRegions: Boolean = false,
    @SerialName(SHOW_ZONES_KEY)
    val showZones: Boolean = false,
    @SerialName(SHOW_SECTIONS_KEY)
    val showSections: Boolean = false
) {

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }

    companion object {
        private const val CAMPAIGN_KEY = "campaign"
        private const val SHOW_REGIONS_KEY = "show_regions"
        private const val SHOW_ZONES_KEY = "show_zones"
        private const val SHOW_SECTIONS_KEY = "show_sections"
    }
}

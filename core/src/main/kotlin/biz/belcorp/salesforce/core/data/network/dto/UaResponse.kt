package biz.belcorp.salesforce.core.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UaResponse(
    @SerialName("region_id")
    var regionId: Long? = null,
    @SerialName("region_code")
    var regionCode: String? = null,
    @SerialName("region_description")
    var regionDescription: String? = null,
    @SerialName("region_manager")
    var regionManager: String? = null,
    @SerialName("manager_email")
    var managerEmail: String? = null,

    @SerialName("zone_id")
    var zoneId: Long? = null,
    @SerialName("zone_code")
    var zoneCode: String? = null,
    @SerialName("zone_description")
    var zoneDescription: String? = null,
    @SerialName("zone_manager")
    var zoneManager: String? = null,
    @SerialName("zone_email")
    var zoneEmail: String? = null,

    @SerialName("section_id")
    var sectionId: Long? = null,
    @SerialName("section_code")
    var sectionCode: String? = null,
    @SerialName("section_description")
    var sectionDescription: String? = null,
    @SerialName("section_manager")
    var sectionManager: String? = null,
    @SerialName("consultant_id")
    var consultantId: String? = null,
    @SerialName("section_type")
    var sectionType: String? = null,
    @SerialName("is_suitable")
    var isSuitable: Boolean? = null,
    @SerialName("is_covered")
    var covered: Boolean? = null,

    @SerialName("zones")
    val zones: List<UaResponse>? = emptyList(),
    @SerialName("sections")
    val sections: List<UaResponse>? = emptyList()

)

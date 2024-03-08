package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault


@Serializable
class ConsultantsParams {

    @SerialName(KEY_COUNTRY)
    var country: String = EMPTY_STRING
    @SerialName(KEY_CAMPAIGN)
    var campaign: String = EMPTY_STRING
    @SerialName(KEY_REGION)
    var region: String = EMPTY_STRING
    @SerialName(KEY_ZONE)
    var zone: String = EMPTY_STRING
    @SerialName(KEY_SECTION)
    var section: String = EMPTY_STRING
    @SerialName(KEY_PAGE)
    var page: Int = DEFAULT_PAGE
    @SerialName(KEY_SIZE)
    var size: Int = DEFAULT_SIZE
    @SerialName(KEY_NAME_FILTER)
    var nameFilter: String = EMPTY_STRING
    @SerialName(KEY_SEGMENT_FILTER)
    var segmentFilter: Array<String> = emptyArray()
    @SerialName(KEY_PHASE)
    var phase: String = EMPTY_STRING
    @SerialName(KEY_TYPE)
    var type: String = "A"
    @SerialName(KEY_IS_PDV)
    var isPdv: Boolean = DEFAULT_IS_PDV
    @SerialName(KEY_LAST_MODIFIED)
    var lastModified: Long = DEFAULT_LAST_MODIFIED

    companion object {

        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_SIZE = 0
        private const val DEFAULT_IS_PDV = false
        private const val DEFAULT_LAST_MODIFIED = 0L

    }

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

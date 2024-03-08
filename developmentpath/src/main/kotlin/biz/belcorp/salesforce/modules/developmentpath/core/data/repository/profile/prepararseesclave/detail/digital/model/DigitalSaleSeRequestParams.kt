package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class DigitalSaleSeRequestParams(
    @SerialName(COUNTRY_KEY)
    var country: String,
    @SerialName(CAMPAIGN_KEY)
    var campaign: List<String>,
    @SerialName(REGION_KEY)
    var region: String,
    @SerialName(ZONE_KEY)
    var zone: String,
    @SerialName(SECTION_KEY)
    var section: String
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

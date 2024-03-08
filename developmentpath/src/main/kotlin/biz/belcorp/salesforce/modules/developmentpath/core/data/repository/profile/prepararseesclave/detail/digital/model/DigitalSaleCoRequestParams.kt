package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.CAMPAIGN_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.CONSULTANT_CODE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.COUNTRY_KEY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class DigitalSaleCoRequestParams(
    @SerialName(COUNTRY_KEY)
    var country: String,
    @SerialName(CAMPAIGN_KEY)
    var campaign: List<String>,
    @SerialName(CONSULTANT_CODE_KEY)
    var consultantCode: String,
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

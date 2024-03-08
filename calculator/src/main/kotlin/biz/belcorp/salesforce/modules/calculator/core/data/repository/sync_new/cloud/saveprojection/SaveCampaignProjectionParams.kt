package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.saveprojection

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.JsonUtil
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.*
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.dto.SaveCampaignProjectionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class SaveCampaignProjectionParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_REGION)
    val region: String? = Constant.EMPTY_STRING,
    @SerialName(KEY_ZONE)
    val zone: String? = Constant.EMPTY_STRING,
    @SerialName(KEY_SECTION)
    val section: String? = Constant.EMPTY_STRING,
    @SerialName(KEY_CAMPAIGN)
    val campaign: String? = Constant.EMPTY_STRING,
    @SerialName(KEY_CAPITALIZATION)
    val capitalization: SaveCampaignProjectionDto.Capitalization,
    @SerialName(KEY_RETENTION_6D6)
    val retention6D6: SaveCampaignProjectionDto.Retention6d6,
    @SerialName(KEY_ORDERS)
    val orders: SaveCampaignProjectionDto.Orders,
    @SerialName(KEY_ACTIVITY_PROJECTED_PERCENTAGE)
    val activityProjectedPercentage: Int? = Constant.NUMBER_ZERO,
    @SerialName(KEY_PEGS_PROJECTED_NEXT_CAMPAIGN)
    val pegsProjectedNextCampaign: Int? = Constant.NUMBER_ZERO,
) {
    fun toJson(): String {
        return JsonUtil.JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

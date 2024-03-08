package biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.*
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_ACHIEVEMENT
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_CAMPAIGN
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_CLASSIFICATION
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_LEVEL
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_PRODUCTIVITY
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_PROFILE
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_REGION
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_SECTION
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_SUBCLASSIFICATION
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.KEY_SALES_FORCE_STATUS_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalesForceStatusDto(
    @SerialName(KEY_SALES_FORCE_STATUS_CAMPAIGN)
    val campaign: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_PROFILE)
    val profile: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_REGION)
    val region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_ZONE)
    val zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_SECTION)
    val section: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_LEVEL)
    val level: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_ACHIEVEMENT)
    val achievement: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_PRODUCTIVITY)
    val productivity: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_CLASSIFICATION)
    val classification: String = Constant.EMPTY_STRING,
    @SerialName(KEY_SALES_FORCE_STATUS_SUBCLASSIFICATION)
    val subclassification: String = Constant.EMPTY_STRING,
)

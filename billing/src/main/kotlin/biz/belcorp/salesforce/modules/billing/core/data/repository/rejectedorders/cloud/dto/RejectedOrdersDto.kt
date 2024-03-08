package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RejectedOrdersDto {

    @Serializable
    data class Data(
        @SerialName(COLLECTION_KEY)
        val rejectedOrders: List<RejectedOrders> = emptyList()
    ) {
        @Serializable
        data class RejectedOrders(
            @SerialName(CAMPAIGN_KEY)
            val campaign: String = Constant.EMPTY_STRING,
            @SerialName(KEY_REGION)
            val region: String = Constant.EMPTY_STRING,
            @SerialName(KEY_ZONE)
            val zone: String = Constant.EMPTY_STRING,
            @SerialName(KEY_SECTION)
            val section: String = Constant.EMPTY_STRING,
            @SerialName(REASONS_KEY)
            val reasons: List<RejectedOrdersDetail> = emptyList()
        ) {
            @Serializable
            data class RejectedOrdersDetail(
                @SerialName(NAME_KEY)
                val name: String = Constant.EMPTY_STRING,
                @SerialName(QUANTITY_KEY)
                val quantity: Int = Constant.NUMERO_CERO
            )
        }
    }

}

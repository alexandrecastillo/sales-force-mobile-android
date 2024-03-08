package biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OrderAmountDto(
    @SerialName(AMOUNTS_KEY)
    val amounts: List<OrderAmount>
) {

    @Serializable
    class OrderAmount(
        @SerialName(CONSULTANT_ID_KEY)
        val consultantId: Int,
        @SerialName(AMOUNT_KEY)
        val amount: Double
    )

}

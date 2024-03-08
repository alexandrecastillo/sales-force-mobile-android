package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TransactionAccountDto(
    @SerialName(COLLECTION_KEY)
    val transactionAccount: List<TransactionAccount>
) {

    @Serializable
    class TransactionAccount(
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_CONSULTANT_ID)
        val consultantId: Int,
        @SerialName(KEY_TRANSACTION_DATE)
        val transactionDate: String,
        @SerialName(KEY_TRANSACTION_DESCRIPTION)
        val transactionDescription: String,
        @SerialName(KEY_TRANSACTION_TYPE)
        val transactionType: String,
        @SerialName(KEY_AMOUNT)
        val amount: Double
    )

}


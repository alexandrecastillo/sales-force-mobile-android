package biz.belcorp.salesforce.core.entities.transactionaccount

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TransactionAccountEntity(
    @Id
    var id: Long = NUMBER_ZERO_LONG,
    var consultantId: Int = NUMBER_ZERO,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var transactionType: String = EMPTY_STRING,
    var amount: Double = ZERO_DECIMAL,
    var transactionDescription: String = EMPTY_STRING,
    var transactionDate: String = EMPTY_STRING
)

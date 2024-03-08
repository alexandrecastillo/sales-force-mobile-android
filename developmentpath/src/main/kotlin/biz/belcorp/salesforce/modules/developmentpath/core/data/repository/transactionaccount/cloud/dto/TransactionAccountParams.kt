package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.KEY_COUNTRY
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class TransactionAccountParams(
    @SerialName(KEY_COUNTRY)
    var country: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CODE_REGION)
    var region: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CODE_ZONE)
    var zone: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CODE_SECTION)
    var section: String = Constant.EMPTY_STRING,
    @SerialName(KEY_CONSULTANT_ID)
    var consultantId: Int = NUMBER_ZERO
) {

    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

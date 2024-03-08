package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_APPLICATION
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_CODE
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_COUNTRY
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_ROLE
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_TYPE_QUERY
import biz.belcorp.salesforce.core.data.repository.terms.cloud.ORIGIN
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class ApproveRequestParams(
    @SerialName(KEY_COUNTRY)
    val country: String,
    @SerialName(KEY_ROLE)
    val role: String,
    @SerialName(KEY_CODE)
    val code: String,
    @SerialName(KEY_TYPE_QUERY)
    val typeQuery: String,
    @SerialName(KEY_APPLICATION)
    val application: String = ORIGIN
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

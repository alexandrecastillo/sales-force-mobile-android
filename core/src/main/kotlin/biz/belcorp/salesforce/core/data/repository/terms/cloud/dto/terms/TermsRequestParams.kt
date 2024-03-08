package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_APPLICATION
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_COUNTRY
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_ROLE
import biz.belcorp.salesforce.core.data.repository.terms.cloud.ORIGIN
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TermsRequestParams(
    @SerialName(KEY_COUNTRY)
    val country: String,
    @SerialName(KEY_ROLE)
    val role: String,
    @SerialName(KEY_APPLICATION)
    val application: String = ORIGIN
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }
}

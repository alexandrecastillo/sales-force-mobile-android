package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.*
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.TermDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

@Serializable
class SaveTermsParams(
    @SerialName(KEY_COUNTRY)
    val country: String,
    @SerialName(KEY_REGION)
    val region: String,
    @SerialName(KEY_ZONE)
    val zone: String,
    @SerialName(KEY_SECTION)
    val section: String,
    @SerialName(KEY_ROLE)
    val role: String,
    @SerialName(KEY_CONSULTANT_CODE)
    val consultantCode: String,
    @SerialName(KEY_USER_CODE)
    val userCode: String,
    @SerialName(KEY_DOCUMENT_NUMBER)
    val documentNumber: String,
    @SerialName(KEY_TERMS)
    val terms: List<TermDto>,
    @SerialName(KEY_APPLICATION)
    val application: String = ORIGIN
) {
    fun toJson(): String {
        return JsonEncodedDefault.encodeToString(serializer(), this)
    }

}

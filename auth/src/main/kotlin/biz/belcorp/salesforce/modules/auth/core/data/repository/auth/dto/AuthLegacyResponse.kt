package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AuthLegacyResponse {

    @SerialName("access_token")
    var accessToken: String? = null

    @SerialName("refresh_token")
    var refreshToken: String? = null

    @SerialName("codRol")
    var roleCode: String? = null

    @SerialName("codUsuario")
    var userCode: String? = null

    @SerialName("codigoConsultora")
    var consultantCode: String? = null

    @SerialName("codPais")
    var countryCode: String? = null

}

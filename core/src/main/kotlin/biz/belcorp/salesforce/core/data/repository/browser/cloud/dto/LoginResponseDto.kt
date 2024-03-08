package biz.belcorp.salesforce.core.data.repository.browser.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginResponseDto {
    @SerialName("access_token")
    var accessToken: String? = null
    @SerialName("campania")
    var campania: String? = null
    @SerialName("correo")
    var correo: String? = null
    @SerialName("segmentoConstancia")
    var segmentoConstancia: String? = null
    @SerialName("esLider")
    var esLider: String? = null
    @SerialName("nivelLider")
    var nivelLider: String? = null
    @SerialName("campaniaInicioLider")
    var campaniaInicioLider: String? = null
    @SerialName("seccionGestionLider")
    var seccionGestionLider: String? = null
}

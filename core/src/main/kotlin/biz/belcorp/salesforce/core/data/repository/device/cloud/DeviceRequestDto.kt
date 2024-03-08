package biz.belcorp.salesforce.core.data.repository.device.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DeviceRequestDto(
    @SerialName("AppID")
    val appID: Int,
    @SerialName("Pais")
    val pais: String,
    @SerialName("RolID")
    val rolId: Int,
    @SerialName("Usuario")
    val usuario: String,
    @SerialName("UUID")
    val uuid: String,
    @SerialName("IMEI")
    val imei: String,
    @SerialName("SO")
    val sistemaOperativo: String = "Android",
    @SerialName("Modelo")
    val modelo: String,
    @SerialName("Version")
    val version: String,
    @SerialName("TokenFCM")
    val tokenFcm: String? = null,
    @SerialName("TopicFCM")
    val topicoFCM: String? = null
)

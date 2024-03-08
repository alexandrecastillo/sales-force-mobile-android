package biz.belcorp.salesforce.core.data.repository.device.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DeviceResponseDto(
    @SerialName("Respuesta")
    val response: Response
) {

    @Serializable
    class Response(
        @SerialName("dispositivoID")
        val deviceId: Long
    )

}

package biz.belcorp.salesforce.core.data.network

import biz.belcorp.salesforce.core.data.repository.device.cloud.DeviceRequestDto
import biz.belcorp.salesforce.core.data.repository.device.cloud.DeviceResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface SomosBelcorpApi {

    @POST("dispositivo/guardar")
    suspend fun saveFirebaseToken(@Body dispositivoRequest: DeviceRequestDto): Response<DeviceResponseDto>

}

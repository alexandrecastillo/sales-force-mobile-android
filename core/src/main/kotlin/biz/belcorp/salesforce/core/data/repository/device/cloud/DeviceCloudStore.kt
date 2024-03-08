package biz.belcorp.salesforce.core.data.repository.device.cloud

import biz.belcorp.salesforce.core.data.network.SomosBelcorpApi
import biz.belcorp.salesforce.core.utils.safeApiCall


class DeviceCloudStore(private val somosBelcorpApi: SomosBelcorpApi) {

    suspend fun saveToken(dispositivoRequest: DeviceRequestDto): Long {
        val response = safeApiCall {
            somosBelcorpApi.saveFirebaseToken(dispositivoRequest)
        }
        return requireNotNull(response).response.deviceId
    }

}

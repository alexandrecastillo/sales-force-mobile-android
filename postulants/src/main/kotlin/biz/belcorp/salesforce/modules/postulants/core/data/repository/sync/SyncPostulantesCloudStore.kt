package biz.belcorp.salesforce.modules.postulants.core.data.repository.sync

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.postulants.core.data.network.SyncPostulantesApi
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.dto.DataResponse
import com.google.gson.GsonBuilder


internal class SyncPostulantesCloudStore(
    private val syncApi: SyncPostulantesApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {

        private const val TAG = "SyncPostulantesResponse =>"

    }

    suspend fun sync(ua: String): LegacySyncResponse.Resultado<DataResponse> {
        val response = apiCallHelper.safeLegacyApiCall { syncApi.sync(ua) }
        val result = requireNotNull(response?.resultado)
        printResult(result)
        return result
    }

    private fun printResult(resultado: LegacySyncResponse.Resultado<DataResponse>) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(resultado.clone())
        println("$TAG $json")
    }

}

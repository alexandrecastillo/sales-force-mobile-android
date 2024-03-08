package biz.belcorp.salesforce.modules.inspires.core.data.repository.sync

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.inspires.core.data.network.InspiresApi
import biz.belcorp.salesforce.modules.inspires.core.data.repository.sync.dto.DataResponse
import com.google.gson.GsonBuilder

class SyncCloudStore(
    private val inspiresApi: InspiresApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {

        private const val TAG = "InspireSyncResponse =>"

    }

    suspend fun sync(fechaSync: Long, ua: String): LegacySyncResponse.Resultado<DataResponse> {
        val response = apiCallHelper.safeLegacyApiCall { inspiresApi.sync(fechaSync, ua) }
        val result = requireNotNull(response?.resultado)
        printResult(result)
        return result
    }

    private fun printResult(resultado: LegacySyncResponse.Resultado<DataResponse>) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(resultado.clone())
        println("***** $TAG $json")
    }

}

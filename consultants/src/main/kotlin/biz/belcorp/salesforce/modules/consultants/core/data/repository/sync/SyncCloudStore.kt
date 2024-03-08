package biz.belcorp.salesforce.modules.consultants.core.data.repository.sync

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultorasApi
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto.SyncResponse
import com.google.gson.GsonBuilder


class SyncCloudStore(
    private val consultorasApi: ConsultorasApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {

        private const val TAG = "ConsultantsSyncResponse =>"

    }

    suspend fun sync(fechaSync: Long, ua: String): SyncResponse.Resultado {
        val response = apiCallHelper.safeLegacyApiCall { consultorasApi.sync(fechaSync, ua) }
        val result = requireNotNull(response?.resultado)
        printResult(result)
        return result
    }

    private fun printResult(resultado: SyncResponse.Resultado) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(resultado.clone())
        println("$TAG $json")
    }

}

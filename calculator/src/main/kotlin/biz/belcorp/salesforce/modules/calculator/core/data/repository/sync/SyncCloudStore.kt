package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.calculator.core.data.network.CalculatorApi
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.dto.SyncResponse
import com.google.gson.GsonBuilder

class SyncCloudStore(
    private val calculatorApi: CalculatorApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {

        private const val TAG = "CalculatorSyncResponse =>"

    }

    suspend fun sync(fechaSync: Long, ua: String): SyncResponse.Resultado {
        val response = apiCallHelper.safeLegacyApiCall { calculatorApi.sync(fechaSync, ua) }
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

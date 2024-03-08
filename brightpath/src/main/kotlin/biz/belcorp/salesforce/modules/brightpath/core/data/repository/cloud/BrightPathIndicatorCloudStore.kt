package biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud

import biz.belcorp.salesforce.core.utils.safeApiCall
import biz.belcorp.salesforce.modules.brightpath.core.data.network.BrightPathApi
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync.SyncResponse
import com.google.gson.GsonBuilder

class BrightPathIndicatorCloudStore(private val brightPathApi: BrightPathApi) {

    companion object {
        private const val TAG = "BrightPathSyncResponse = >"
    }

    suspend fun sync(ua: String): SyncResponse.Result {
        val response = safeApiCall { brightPathApi.sync(ua) }
        val result = requireNotNull(response?.result)
        printResult(result)
        return result
    }

    private fun printResult(result: SyncResponse.Result) {
        val gson = GsonBuilder().create()
        val json = gson.toJson(result.data)
        println("$TAG $json")
    }
}

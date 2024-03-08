package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.sync.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync.SyncResponse
import io.reactivex.Single

class SyncCloudStore(
    private val syncApi: SyncRestApi,
    private val apiHelper: SafeApiCallHelper
) {

    fun sincronizar(fechaSync: Long, ua: String): Single<SyncResponse.Resultado> {
        return apiHelper.safeSingleApiCall {
            syncApi.sincronizar(fechaSync, ua)
        }.map {
            it.resultado }
    }

}

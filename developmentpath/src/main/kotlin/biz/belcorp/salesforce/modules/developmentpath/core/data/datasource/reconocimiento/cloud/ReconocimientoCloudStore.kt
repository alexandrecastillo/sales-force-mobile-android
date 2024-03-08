package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.reconocimientos.CalificarVisitaRequest
import io.reactivex.Completable

class ReconocimientoCloudStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun enviar(request: List<CalificarVisitaRequest>): Completable {
        return apiCallHelper.safeCompletableApiCall {
            syncRestApi.reconocimientos(request)
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos.EventoRddXUaRequest
import io.reactivex.Completable

class EventosXUaDataCloudStore(
    private val restApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun enviar(eventos: List<EventoRddXUaRequest>): Completable {
        return apiCallHelper.safeCompletableApiCall {
            restApi.editarEventosXUa(eventos)
        }
    }
}

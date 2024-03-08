package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import io.reactivex.Completable

class ReconocimientosCloudStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun subirReconocimientos(reconocimientos: List<ComportamientoDetalleEntity>): Completable {
        return apiCallHelper.safeCompletableApiCall {
            syncRestApi.guardarComportamientos(
                reconocimientos
            )
        }
    }

}

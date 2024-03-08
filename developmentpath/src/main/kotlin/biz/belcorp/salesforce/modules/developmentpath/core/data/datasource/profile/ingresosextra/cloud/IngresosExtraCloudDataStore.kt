package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import io.reactivex.Single

class IngresosExtraCloudDataStore(
    private val syncApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun sincronizar(data: List<OtraMarcaDetalleEntity>): Single<List<OtraMarcaDetalleEntity>> {
        return apiCallHelper.safeSingleApiCall {
            syncApi.grabarMarcaVentaConsultora(data).map { it.resultado }
        }
    }
}

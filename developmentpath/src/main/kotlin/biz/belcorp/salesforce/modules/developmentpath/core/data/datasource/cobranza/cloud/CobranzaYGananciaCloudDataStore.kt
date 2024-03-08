package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.cobranza.CobranzaYGananciaResponse
import io.reactivex.Single

class CobranzaYGananciaCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun obtener(llaveUA: LlaveUA): Single<CobranzaYGananciaResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.obtenerCobranzaGanancia(
                llaveUA.codigoRegion,
                llaveUA.codigoZona, llaveUA.codigoSeccion
            )
        }
    }
}

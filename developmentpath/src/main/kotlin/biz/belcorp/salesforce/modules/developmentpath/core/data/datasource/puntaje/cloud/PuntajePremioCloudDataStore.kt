package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.puntaje.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje.PuntajePremio
import io.reactivex.Single

class PuntajePremioCloudDataStore(
    private val syncApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun obtenerPuntaje(request: PuntajePremio.Request): Single<PuntajePremio> {
        return apiCallHelper.safeSingleApiCall {
            syncApi.obtenerPuntajesConcurso(
                request.isoPais,
                request.codigoConsultora, request.campania, request.idConsultora
            )
        }.map { it.resultado.parse() }
    }

}

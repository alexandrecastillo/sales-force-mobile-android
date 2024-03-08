package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import io.reactivex.Completable

class HorarioVisitaCloudDataStore(
    private val syncApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun sincronizar(data: List<HorarioVisitaConsultoraEntity>): Completable {
        return apiCallHelper.safeCompletableApiCall {
            syncApi.guardarHorarioVisita(
                data
            )
        }
    }
}

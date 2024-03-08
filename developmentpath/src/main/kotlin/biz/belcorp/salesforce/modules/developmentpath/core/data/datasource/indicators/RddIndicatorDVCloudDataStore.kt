package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorVisitasRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import io.reactivex.Single

class RddIndicatorDVCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun obtenerIndicadores(campania: String, ua: String): Single<List<IndicadorVisitasRDDEntity>> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.obtenerIndicadores(campania, ua).map { it.resultado }
        }
    }

}

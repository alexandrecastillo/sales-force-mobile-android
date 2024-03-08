package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.plandetalle.PlanDetalleResponse
import io.reactivex.Single

class PlanDetalleCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun obtener(planId: Long): Single<PlanDetalleResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.obtenerPlanDetalle(planId)
        }
    }

}

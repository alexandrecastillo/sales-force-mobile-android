package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.dashboard.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.dashboard.DashboardResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.DashboardRepository
import io.reactivex.Single

class DashboardCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun obtener(request: DashboardRepository.Request): Single<DashboardResponse> {
        val param = "${request.planId ?: 0}"
            .concatenarSiNoEsNull(request.llaveUA.codigoRegion)
            .concatenarSiNoEsNull(request.llaveUA.codigoZona)
            .concatenarSiNoEsNull(request.llaveUA.codigoSeccion)

        return apiCallHelper.safeSingleApiCall {
            syncRestApi
                .obtenerDashboard(param)
        }
    }

    private fun String.concatenarSiNoEsNull(codigoRegion: String?): String {
        return if (codigoRegion != null) {
            "$this/$codigoRegion"
        } else {
            this
        }
    }

}

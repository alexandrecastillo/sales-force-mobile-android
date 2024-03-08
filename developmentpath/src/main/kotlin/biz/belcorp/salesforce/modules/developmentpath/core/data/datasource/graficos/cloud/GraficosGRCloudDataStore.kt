package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.GraficosApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos.GraficoGrResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import io.reactivex.Single

class GraficosGRCloudDataStore(
    private val graficosApi: GraficosApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun descargarGraficos(llaveUA: LlaveUA): Single<GraficoGrResponse> {
        return apiCallHelper.safeSingleApiCall {
            graficosApi.descargarGraficosGr(
                llaveUA.codigoRegion ?: Constant.EMPTY_STRING
            )
        }
    }
}

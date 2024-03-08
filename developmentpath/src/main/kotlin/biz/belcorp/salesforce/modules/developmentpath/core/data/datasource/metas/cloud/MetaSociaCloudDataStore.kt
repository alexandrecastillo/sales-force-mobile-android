package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.anotaciones.AnotacionesResponse
import io.reactivex.Single

class MetaSociaCloudDataStore(
    private val api: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun guardar(entidades: List<AnotacionEntity>): Single<AnotacionesResponse> {
        return apiCallHelper.safeSingleApiCall {
            api.crearAnotaciones(entidades)
        }
    }

    fun eliminar(entidades: List<AnotacionEntity>): Single<BaseResponse> {
        return apiCallHelper.safeSingleApiCall {
            api.eliminarAnotaciones(entidades)
        }
    }

    fun actualizar(entidades: List<AnotacionEntity>): Single<BaseResponse> {
        return apiCallHelper.safeSingleApiCall {
            api.editarAnotaciones(entidades)
        }
    }
}

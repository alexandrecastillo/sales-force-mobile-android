package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.cloud

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.anotaciones.AnotacionesResponse
import io.reactivex.Single

class AnotacionCloudStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun enviarEliminados(entidades: List<AnotacionEntity>): Single<BaseResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.eliminarAnotaciones(entidades)
        }
    }

    fun enviarEditados(entidades: List<AnotacionEntity>): Single<BaseResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.editarAnotaciones(entidades)
        }
    }

    fun enviarNuevos(entidades: List<AnotacionEntity>): Single<AnotacionesResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.crearAnotaciones(entidades)
        }
    }

}

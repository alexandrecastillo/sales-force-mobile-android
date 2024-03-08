package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.cloud

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.acuerdos.AcuerdosResponse
import io.reactivex.Single

class AcuerdosCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun enviarEditados(entidades: List<AcuerdoEntity>): Single<BaseResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.editarAcuerdos(entidades)
        }
    }

    fun enviarEliminados(entidades: List<AcuerdoEntity>): Single<BaseResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.eliminarAcuerdos(entidades)
        }
    }

    fun enviarNuevos(entidades: List<AcuerdoEntity>): Single<AcuerdosResponse> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.crearAcuerdos(entidades)
        }
    }

}

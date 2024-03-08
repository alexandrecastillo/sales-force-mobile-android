package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoSEApiModel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFocoSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.GuardadoFocosAsyncRepository
import com.google.gson.Gson
import io.reactivex.Completable

class GuardadoFocosCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) : GuardadoFocosAsyncRepository {

    private val gson = Gson()

    override fun guardarSE(detallesFoco: List<DetalleFocoSE>): Completable {
        val request = detallesFoco.map {
            DetalleFocoSEApiModel(
                consultoraID = it.personaId,
                campania = it.campania,
                focos = gson.toJson(it.focos)
            )
        }
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.focosSE(request)
        }.toCompletable()
    }

    override fun guardar(detallesFoco: List<DetalleFoco>): Completable {
        val request = detallesFoco.map {
            DetalleFocoApiRequest(
                campania = it.campania,
                region = it.region,
                zona = it.zona,
                seccion = it.seccion,
                usuario = it.usuario,
                focos = gson.toJson(it.focos)
            )
        }
        return apiCallHelper.safeSingleApiCall {
            syncRestApi
                .focos(request)
        }.toCompletable()
    }

}

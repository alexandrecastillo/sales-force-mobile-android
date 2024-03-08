package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.perfil.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.DatosPerfilConsultoraResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.DatosPerfilOtrosResponse

class DatosPerfilCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun obtenerConsultora(personaId: Long, region: String): DatosPerfilConsultoraResponse {
        val response = apiCallHelper.safeLegacyApiCall {
            syncRestApi.obtenerDatosPerfilConsultora(personaId, region)
        }
        return requireNotNull(response)
    }

    suspend fun obtenerOtrosRoles(personaId: Long, rol: Rol): DatosPerfilOtrosResponse {
        val response = apiCallHelper.safeLegacyApiCall {
            syncRestApi.obtenerDatosPerfilOtrosRoles(personaId, rol.codigoRol)
        }
        return requireNotNull(response)
    }
}

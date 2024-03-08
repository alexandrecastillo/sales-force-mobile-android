package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.PerfilApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.concursos.ConcursosResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona

class ConcursosDBCloudStore(
    private val perfilApi: PerfilApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun sync(person: Persona, country: String): ConcursosResponse {
        val response = apiCallHelper.safeLegacyApiCall {
            perfilApi.obtenerConcursos(
                country,
                person.documento,
                person.unidadAdministrativa.campania.codigo
            )
        }
        return requireNotNull(response)
    }

}

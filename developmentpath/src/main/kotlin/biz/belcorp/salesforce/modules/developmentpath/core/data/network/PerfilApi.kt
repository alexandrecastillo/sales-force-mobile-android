package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.concursos.ConcursosResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PerfilApi {

    @POST("FFVVOnlineService/api/consultora/insertarmetalist")
    fun guardar(@Body metasConsultora: List<MetasEntity>): Single<MetasEntity>

    @GET("FFVVOnlineService/api/consultora/concursos/{pais}/{documentoPersona}/{campania}")
    suspend fun obtenerConcursos(
        @Path("pais") pais: String,
        @Path("documentoPersona") documentoPersona: String,
        @Path("campania") campania: String
    ): Response<ConcursosResponse>
}

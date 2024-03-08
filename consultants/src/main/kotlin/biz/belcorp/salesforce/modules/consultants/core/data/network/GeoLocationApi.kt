package biz.belcorp.salesforce.modules.consultants.core.data.network

import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.cloud.dto.GeoLocationRequest
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GeoLocationApi {

    @POST("FFVVOnlineService/api/consultora/insertargeoreferencia")
    suspend fun saveGeoLocation(@Body request: GeoLocationRequest): Response<JsonElement>

}

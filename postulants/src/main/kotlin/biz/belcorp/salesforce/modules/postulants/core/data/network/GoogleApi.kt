package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.modules.postulants.core.data.entities.google.PredictionApiResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {

    @GET("place/autocomplete/json")
    fun autoCompletePlaces(
        @Query("key") key: String,
        @Query("types") types: String,
        @Query("components", encoded = true) componentes: String,
        @Query("input") input: String
    ): Single<PredictionApiResponse>

    @GET("place/details/json")
    fun detailPlace(
        @Query("key") key: String,
        @Query("place_id") placeId: String
    ): Single<JsonObject>

}

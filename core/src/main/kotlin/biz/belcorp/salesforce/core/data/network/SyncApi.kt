package biz.belcorp.salesforce.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.repository.campania.dto.CampaniaDataResponse
import biz.belcorp.salesforce.core.data.repository.searchfilters.dto.SearchFiltersDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SyncApi {

    @GET("FFVVSyncService/api/sincronizacion/v2/searchfilters/{ua}")
    suspend fun searchfilters(@Path("ua") ua: String): Response<LegacySyncResponse<SearchFiltersDataResponse>>

    @GET("FFVVSyncService/api/sincronizacion/v2/campaigns/{ua}")
    suspend fun campaigns(@Path("ua") ua: String): Response<LegacySyncResponse<CampaniaDataResponse>>

}

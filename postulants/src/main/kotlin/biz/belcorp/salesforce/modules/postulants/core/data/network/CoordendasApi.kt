package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.CoordenadasRequest
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.CoordendasResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface CoordendasApi {

    @POST("location/LatLng")
    fun getCoordinatesByDirection(@Body request: CoordenadasRequest): Single<CoordendasResponse>

}

package biz.belcorp.salesforce.modules.postulants.core.data.repository.maps

import biz.belcorp.salesforce.modules.postulants.BuildConfig
import biz.belcorp.salesforce.modules.postulants.core.data.network.GoogleApi
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.DetailPlace
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import io.reactivex.Single

class GoogleCloudStore(
    private val googleApi: GoogleApi,
    private val mapper: GoogleMapper
) {

    fun buscarLugares(tipo: String = "address", isoPais: String, cadenaBusqueda: String): Single<List<Place>> {
        val componentQuery = "country:$isoPais"
        return googleApi.autoCompletePlaces(
            BuildConfig.GOOGLE_API_KEY, tipo, componentQuery, cadenaBusqueda
        ).map { mapper.map(it) }
    }

    fun buscarDetalleLugar(placeId: String): Single<DetailPlace?> {
        return googleApi.detailPlace(
            BuildConfig.GOOGLE_API_KEY, placeId
        ).map { mapper.map(it) }
    }
}

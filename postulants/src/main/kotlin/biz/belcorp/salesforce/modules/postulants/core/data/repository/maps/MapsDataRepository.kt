package biz.belcorp.salesforce.modules.postulants.core.data.repository.maps

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.DetailPlace
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.MapsRepository
import io.reactivex.Single

class MapsDataRepository
constructor(private val googleCloudStore: GoogleCloudStore) : MapsRepository {

    override fun buscarLugares(isoPais: String, cadenaBusqueda: String): Single<List<Place>> {
        return googleCloudStore.buscarLugares(isoPais = isoPais, cadenaBusqueda = cadenaBusqueda)
    }

    override fun buscarDetalleLugar(placeId: String): Single<DetailPlace?> {
        return googleCloudStore.buscarDetalleLugar(placeId)
    }

}

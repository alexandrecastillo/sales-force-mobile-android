package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.DetailPlace
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import io.reactivex.Single

interface MapsRepository {

    fun buscarLugares(isoPais: String, cadenaBusqueda: String): Single<List<Place>>

    fun buscarDetalleLugar(placeId: String): Single<DetailPlace?>

}

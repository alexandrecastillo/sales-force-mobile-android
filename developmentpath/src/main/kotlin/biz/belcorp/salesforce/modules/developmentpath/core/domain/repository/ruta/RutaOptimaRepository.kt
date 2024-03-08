package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.PeticionRutaOptima
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import io.reactivex.Single

interface RutaOptimaRepository {
    fun solicitarRuta(peticion: PeticionRutaOptima): Single<List<RespuestaRuta.LatLon>>
}

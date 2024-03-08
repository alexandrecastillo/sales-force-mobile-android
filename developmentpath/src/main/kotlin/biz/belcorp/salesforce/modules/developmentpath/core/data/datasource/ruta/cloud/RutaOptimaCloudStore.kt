package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.cloud

import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.RespuestaRutaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.GoogleApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.RutaApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import io.reactivex.Single

class RutaOptimaCloudStore(private val googleApi: GoogleApi,
                           private val mapper: RespuestaRutaMapper
) {

    fun obtenerRutaOptima(request: RutaApiRequest): Single<RespuestaRuta> {
        return googleApi.rutaOptima(request.buildUrl())
            .map { mapper.parseToRespuesta(it) }
    }

}

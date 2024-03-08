package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.geolocation

import biz.belcorp.ffvv.data.net.dto.georreferencia.GeorreferenciaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.GeorreferenciaApi
import io.reactivex.Completable

class GeolocationCloudDataStore(private val geoApi: GeorreferenciaApi) {

    fun saveGeolocation(codigo: String?, latitud: String, longitud: String): Completable {

        val request = GeorreferenciaRequest()

        request.codigo = codigo
        request.latitud = latitud
        request.longitud = longitud

        return geoApi.guardarGeorreferencia(request)
    }

}

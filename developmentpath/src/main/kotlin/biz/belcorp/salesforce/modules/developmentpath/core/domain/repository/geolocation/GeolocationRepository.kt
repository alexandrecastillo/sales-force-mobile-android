package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.geolocation

import io.reactivex.Completable

interface GeolocationRepository {

    fun saveGeolocation(codigo: String?, latitud: String, longitud: String): Completable

}

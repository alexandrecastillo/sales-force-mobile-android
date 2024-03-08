package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.geolocation

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.geolocation.GeoLocationDBdataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.geolocation.GeolocationCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.geolocation.GeolocationRepository
import io.reactivex.Completable

class GeolocationDataRepository(
    private val cloudStore: GeolocationCloudDataStore,
    private val dbStore: GeoLocationDBdataStore,
    private val sesionManager: SessionRepository
) : GeolocationRepository {

    override fun saveGeolocation(codigo: String?, latitud: String, longitud: String): Completable {
        return saveGeolocationLocal(codigo, latitud, longitud)
            .andThen(saveGeolocationCloud(codigo, latitud, longitud))
    }

    private fun saveGeolocationCloud(codigo: String?, latitud: String, longitud: String): Completable {
        val codigoConsultora = codigo ?: (sesionManager.getSession()?.persona as? SociaEmpresariaRdd)?.codigo
        return cloudStore.saveGeolocation(codigoConsultora, latitud, longitud)
    }

    private fun saveGeolocationLocal(codigo: String?, latitud: String, longitud: String): Completable {
        return if (codigo != null) {
            dbStore.saveGeolocation(codigo, latitud, longitud)
        } else {
            Completable.complete()
        }
    }

}

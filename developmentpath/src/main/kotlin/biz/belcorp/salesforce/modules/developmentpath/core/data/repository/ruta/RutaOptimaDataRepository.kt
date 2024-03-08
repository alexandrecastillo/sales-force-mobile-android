package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta

import biz.belcorp.salesforce.core.entities.sql.geo.RutaOptimaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.cloud.RutaOptimaCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.ConfiguracionRutaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.RutaOptimaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.RutaOptimaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Optional
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.calcularDistancia
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toPair
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.PeticionRutaOptima
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.RutaOptimaRepository
import io.reactivex.Single

class RutaOptimaDataRepository(private val rutaCloudStore: RutaOptimaCloudStore,
                               private val rutaDBD: RutaOptimaDBDataStore,
                               private val configuracionDB: ConfiguracionRutaDBDataStore,
                               private val rutaOptimaMapper: RutaOptimaMapper
)
    : RutaOptimaRepository {

    override fun solicitarRuta(peticion: PeticionRutaOptima): Single<List<RespuestaRuta.LatLon>> {
        return solicitarRutaLocal(peticion)
            .flatMap {
                when (it) {
                    is Optional.Some -> Single.just(it.element())
                    is Optional.None -> solicitarRutaCloud(peticion)
                }
            }
            .map { rutaOptimaMapper.parse(it) }
    }

    private fun solicitarRutaLocal(peticion: PeticionRutaOptima): Single<Optional<RutaOptimaEntity>> {
        return Single.create { singleEmitter ->

            val distancia = configuracionDB.get(peticion.rol)?.distanciaMin ?: 100
            val rutas = rutaDBD.recuperarSimilares(peticion)
            val rutaOptima = rutas.find { it.estaAMenosDe(distancia, peticion.origen) }

            singleEmitter.onSuccess(Optional.create(rutaOptima))
        }
    }

    private fun solicitarRutaCloud(peticion: PeticionRutaOptima): Single<RutaOptimaEntity> {
        return rutaCloudStore.obtenerRutaOptima(rutaOptimaMapper.parseToRequest(peticion))
            .map {
                rutaDBD.guardar(peticion, it)
                rutaDBD.recuperarUltimo()
            }
    }

    fun RutaOptimaEntity.estaAMenosDe(distancia: Int, punto: String): Boolean {
        return this.origen.toPair().calcularDistancia(punto.toPair()) <= distancia
    }


}

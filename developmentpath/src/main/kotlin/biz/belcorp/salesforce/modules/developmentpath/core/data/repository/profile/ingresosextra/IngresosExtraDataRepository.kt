package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.ingresosextra

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.cloud.IngresosExtraCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.data.IngresosExtraLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.ingresosextra.OtraMarcaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.IngresosExtraRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants
import io.reactivex.Completable
import io.reactivex.Single

class IngresosExtraDataRepository(
    private val cloudDataStore: IngresosExtraCloudDataStore,
    private val localDataStore: IngresosExtraLocalDataStore,
    private val mapper: OtraMarcaEntityMapper
) : IngresosExtraRepository {
    override fun obtenerMarcas(ua: LlaveUA, personaId : Long): Single<List<OtraMarca>> {
        return doOnSingle {
            val marcas = localDataStore.obtenerTodasLasMarcas()
            val result = ArrayList<OtraMarca>()
            marcas.forEach {
                val marcaEntity = OtraMarca()
                marcaEntity.marcaId = it.marcaId
                marcaEntity.consultoraId = ua.consultoraId ?: personaId
                marcaEntity.codigoRegion = ua.codigoRegion ?: Constants.GUION
                marcaEntity.codigoZona = ua.codigoZona ?: Constants.GUION
                marcaEntity.codigoSeccion = ua.codigoSeccion ?: Constants.GUION
                val detalle = localDataStore.obtenerMarcaDetalle(it.marcaId, ua, personaId)
                marcaEntity.marcaDetalleId = detalle?.marcaDetalleId ?: Constants.MENOS_UNO.toLong()
                marcaEntity.checked = detalle?.checked ?: false
                marcaEntity.name = it.name
                marcaEntity.iconoId = it.iconoId ?: Constants.MENOS_UNO
                marcaEntity.orden = it.orden ?: Constants.CERO
                marcaEntity.showFront = it.showFront
                marcaEntity.categoria = detalle?.categoria.toString()
                marcaEntity.campania = detalle?.campania.toString()
                result.add(marcaEntity)
            }
            result
        }
    }

    override fun actualizarMarca(model: OtraMarca): Completable {
        val entity = mapper.reverseMap(model)
        return localDataStore.actualizarMarcaDetalle(entity)
    }

    override fun actualizarMarcaList(data: List<OtraMarca>): Completable {
        val entityList = mapper.reverseMap(data)
        return localDataStore.actualizarMarcaDetalleList(entityList)
    }

    override fun sincronizar(): Completable {
        return localDataStore.obtenerMarcaDetalleNoEnviadas()
            .filter { !it.isNullOrEmpty() }
            .flatMap { cloudDataStore.sincronizar(it).toMaybe() }
            .flatMapCompletable { localDataStore.actualizarMarcaDetalleList(it) }
            .andThen(localDataStore.marcarMarcasDetalleComoEnviadas())
    }
}

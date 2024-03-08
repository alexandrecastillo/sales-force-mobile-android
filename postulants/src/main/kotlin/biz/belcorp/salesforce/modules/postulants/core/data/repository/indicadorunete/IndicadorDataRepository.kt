package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete


import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource.IndicadorCloudDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource.IndicadorDBDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers.DetalleIndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers.SeccionEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.IndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.IndicadorRepository
import io.reactivex.Observable
import io.reactivex.Single

class IndicadorDataRepository(
    private val indicadorCloudDataStore: IndicadorCloudDataStore,
    private val indicadorDBDataStore: IndicadorDBDataStore,
    private val indicadorEntityDataMapper: IndicadorEntityDataMapper,
    private val seccionEntityDataMapper: SeccionEntityDataMapper,
    private val detalleIndicadorEntityDataMapper: DetalleIndicadorEntityDataMapper
) : IndicadorRepository {

    override fun indicadorUneteCloud(
        pais: String, zona: String, region: String, rol: String, value: String?
    ): Single<List<IndicadorUnete>> {
        return indicadorCloudDataStore
            .indicadorUneteCloud(pais, zona, region, rol, value)
            .map { indicadorEntityDataMapper.parseListaIndicadorUnete(it) }
    }

    override fun saveIndicadorUnete(listaIndicador: List<IndicadorUnete>): Observable<Boolean> {
        return indicadorDBDataStore
            .saveIndicadorUnete(
                indicadorEntityDataMapper.parseListaIndicadorUneteEntity(listaIndicador)
            )
    }

    override fun indicadorUneteDB(rol: String, value: String): Observable<IndicadorUnete> {
        return indicadorDBDataStore
            .indicadorUneteDB(rol, value)
            .map { indicadorEntityDataMapper.parseIndicadorUnete(it) }
    }

    override fun getDetalleIndicadorCloudUnete(
        pais: String, zona: String, region: String, codRol: String
    ): Single<List<DetalleIndicadorUnete>> {
        return indicadorCloudDataStore
            .getDetalleIndicadorUnete(pais, zona, region, codRol)
            .map { detalleIndicadorEntityDataMapper.parseDetalleIndicadorUneteEntityList(it) }
    }

    override fun saveDetalleIndicadorUnete(listaDetalleIndicador: List<DetalleIndicadorUnete>): Observable<Boolean> {
        return indicadorDBDataStore
            .updateDetalleIndicadorUnete(
                detalleIndicadorEntityDataMapper.parseDetalleIndicadorUneteList(
                    listaDetalleIndicador
                )
            )
    }

    override fun sections(): Single<List<Seccion>> {
        return indicadorDBDataStore
            .sections()
            .map { seccionEntityDataMapper.parse(it) }
    }

    override fun getDetalleIndicadorUnete(
        pais: String, zona: String, region: String, codRol: String
    ): Single<List<DetalleIndicadorUnete>> {
        return indicadorDBDataStore
            .getDetalleIndicadorUnete(pais, zona, region, codRol)
            .map { detalleIndicadorEntityDataMapper.parseDetalleIndicadorUneteEntityList(it) }
    }

}

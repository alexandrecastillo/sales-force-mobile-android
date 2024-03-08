package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource

import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.unete.IndicadorUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import biz.belcorp.salesforce.modules.postulants.core.data.network.IndicadorApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.IndicadorUneteRequest
import io.reactivex.Observable
import io.reactivex.Single

class IndicadorCloudDataStore(
    private val indicadorApi: IndicadorApi
) : IndicadorDataStore {

    override fun indicadorUneteCloud(
        pais: String, zona: String, region: String, rol: String, value: String?
    ): Single<List<IndicadorUneteEntity>> {
        val request = IndicadorUneteRequest()
        request.pais = pais
        request.region = region
        request.zona = zona
        request.rol = rol
        request.seccion = value
        return indicadorApi.cabeceraIndicadorUnete(request).map { it.resultado }
    }

    override fun saveIndicadorUnete(listaIndicador: List<IndicadorUneteEntity>?): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun indicadorUneteDB(rol: String, value: String): Observable<IndicadorUneteEntity> {
        throw UnsupportedOperationException()
    }

    override fun sections(): Single<List<SeccionEntity>> {
        throw UnsupportedOperationException()
    }

    override fun getDetalleIndicadorUnete(
        pais: String,
        zona: String,
        region: String,
        codRol: String
    ): Single<List<DetalleIndicadorUneteEntity>> {
        val request = IndicadorUneteRequest()
        request.pais = pais
        request.region = region
        request.zona = zona
        request.rol = codRol
        return indicadorApi.detalleIndicadorUnete(request).map { it.resultado }
    }

    override fun updateDetalleIndicadorUnete(detalleIndicadorUneteList: List<DetalleIndicadorUneteEntity>): Observable<Boolean> {
        throw UnsupportedOperationException()
    }
}

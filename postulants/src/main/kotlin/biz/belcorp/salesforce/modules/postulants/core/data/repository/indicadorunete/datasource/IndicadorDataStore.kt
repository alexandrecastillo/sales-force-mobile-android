package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource

import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.unete.IndicadorUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import io.reactivex.Observable
import io.reactivex.Single

interface IndicadorDataStore {

    fun indicadorUneteCloud(
        pais: String, zona: String, region: String, rol: String, value: String?
    ): Single<List<IndicadorUneteEntity>>

    fun saveIndicadorUnete(listaIndicador: List<IndicadorUneteEntity>?): Observable<Boolean>

    fun indicadorUneteDB(rol: String, value: String): Observable<IndicadorUneteEntity>

    fun getDetalleIndicadorUnete(
        pais: String, zona: String, region: String, codRol: String
    ): Single<List<DetalleIndicadorUneteEntity>>

    fun updateDetalleIndicadorUnete(detalleIndicadorUneteList: List<DetalleIndicadorUneteEntity>): Observable<Boolean>

    fun sections(): Single<List<SeccionEntity>>
}

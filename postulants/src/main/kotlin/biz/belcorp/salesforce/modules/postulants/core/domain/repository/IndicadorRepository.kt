package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.IndicadorUnete
import io.reactivex.Observable
import io.reactivex.Single

interface IndicadorRepository {

    fun indicadorUneteCloud(
        pais: String, zona: String, region: String, rol: String, value: String?
    ): Single<List<IndicadorUnete>>
    fun saveIndicadorUnete(listaIndicador: List<IndicadorUnete>): Observable<Boolean>
    fun indicadorUneteDB(rol: String, value: String): Observable<IndicadorUnete>
    fun saveDetalleIndicadorUnete(listaDetalleIndicador: List<DetalleIndicadorUnete>): Observable<Boolean>
    fun getDetalleIndicadorUnete(
        pais: String, zona: String, region: String, codRol: String
    ): Single<List<DetalleIndicadorUnete>>

    fun getDetalleIndicadorCloudUnete(
        pais: String, zona: String, region: String, codRol: String
    ): Single<List<DetalleIndicadorUnete>>

    fun sections(): Single<List<Seccion>>
}

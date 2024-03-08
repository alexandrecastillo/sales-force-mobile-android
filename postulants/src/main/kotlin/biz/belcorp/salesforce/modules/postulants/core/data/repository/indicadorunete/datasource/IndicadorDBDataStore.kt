package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity_Table
import biz.belcorp.salesforce.core.entities.sql.unete.IndicadorUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.IndicadorUneteEntity_Table
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.insert
import biz.belcorp.salesforce.modules.postulants.core.data.exceptions.DBDataStoreException
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.sql.language.From
import io.reactivex.Observable
import io.reactivex.Single

class IndicadorDBDataStore : IndicadorDataStore {

    override fun saveIndicadorUnete(listaIndicador: List<IndicadorUneteEntity>?): Observable<Boolean> {
        return Observable.create {
            try {
                FlowManager.getDatabase(AppDatabase::class.java).executeTransaction { _ ->
                    Delete.table(IndicadorUneteEntity::class.java)
                    insertIndicator(listaIndicador)
                    it.onNext(true)
                }
            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    private fun insertIndicator(listaIndicador: List<IndicadorUneteEntity>?) {
        listaIndicador?.insert()
    }

    override fun indicadorUneteDB(rol: String, value: String): Observable<IndicadorUneteEntity> {
        return Observable.create { subscriber ->
            val queryUnete: From<IndicadorUneteEntity> = (select from IndicadorUneteEntity::class)
            val indicador = when (rol) {
                Rol.GERENTE_ZONA.codigoRol, Rol.GERENTE_REGION.codigoRol -> queryGZandGR(
                    value,
                    queryUnete
                )
                else -> queryUnete.querySingle()
            }
            indicador?.let { subscriber.onNext(it) }
            subscriber.onComplete()
        }
    }

    private fun queryGZandGR(
        seccion: String,
        queryUnete: From<IndicadorUneteEntity>
    ): IndicadorUneteEntity? {
        return if (seccion.isNotEmpty()) {
            (queryUnete where (IndicadorUneteEntity_Table.Seccion eq seccion)).querySingle()
        } else {
            (queryUnete where (IndicadorUneteEntity_Table.Seccion.isNull) or (IndicadorUneteEntity_Table.Seccion eq "-")).querySingle()
        }
    }

    override fun indicadorUneteCloud(
        pais: String, zona: String, region: String, rol: String, value: String?
    ): Single<List<IndicadorUneteEntity>> {
        throw UnsupportedOperationException()
    }

    override fun getDetalleIndicadorUnete(
        pais: String, zona: String, region: String, codRol: String
    ): Single<List<DetalleIndicadorUneteEntity>> {
        return doOnSingle { requireNotNull((select from DetalleIndicadorUneteEntity::class).queryList()) }
    }

    override fun updateDetalleIndicadorUnete(detalleIndicadorUneteList: List<DetalleIndicadorUneteEntity>): Observable<Boolean> {
        return Observable.create {
            try {
                FlowManager.getDatabase(AppDatabase::class.java)
                    .executeTransaction { _ ->
                        Delete.table(DetalleIndicadorUneteEntity::class.java)
                        detalleIndicadorUneteList.forEach { item ->
                            item.insert()
                        }
                        it.onNext(true)
                    }
            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    override fun sections(): Single<List<SeccionEntity>> {
        return doOnSingle { requireNotNull(select from SeccionEntity::class).orderBy(SeccionEntity_Table.Codigo, true).queryList() }
    }
}

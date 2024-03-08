package biz.belcorp.salesforce.core.data.repository.campania

import biz.belcorp.salesforce.core.data.repository.campania.dto.CampaniaDataResponse
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Single

class CampaniasDataStore {

    fun obtenerCampaniaActual(llaveUa: LlaveUA): Single<CampaniaUaEntity> {
        return doOnSingle { requireNotNull(obtenerCampaniaSincrono(llaveUa)) }
    }

    fun obtenerCampanias(llaveUa: LlaveUA): Single<List<CampaniaUaEntity>> {
        return doOnSingle { obtenerCampaniasSincrono(llaveUa) }
    }

    fun obtenerPenultimasCampanias(llaveUa: LlaveUA, limite: Int): Single<List<CampaniaUaEntity>> {
        return doOnSingle { obtenerPenultimasCampaniasSincrono(llaveUa, limite) }
    }

    fun obtenerCampaniaAnterior(llaveUa: LlaveUA): Single<CampaniaUaEntity> {
        return doOnSingle {
            obtenerCampaniaAnteriorSincrono(llaveUa)
        }
    }

    fun obtenerCampaniaAnteriorSincrono(llaveUa: LlaveUA): CampaniaUaEntity {
        val campanias = obtenerCampaniasSincrono(llaveUa)
        require(campanias.size > 1)
        return campanias[1]
    }

    fun obtenerCampaniaSincrono(llaveUa: LlaveUA): CampaniaUaEntity? {
        val query = (select
            from CampaniaUaEntity::class
            where (CampaniaUaEntity_Table.Region eq llaveUa.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona eq llaveUa.codigoZona.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Seccion eq llaveUa.codigoSeccion.guionSiVacioONull())
            orderBy (CampaniaUaEntity_Table.Orden.asc()))
        return query.querySingle()
    }

    fun obtenerCampaniasSincrono(llaveUa: LlaveUA): List<CampaniaUaEntity> {
        val query = (select
            from CampaniaUaEntity::class
            where (CampaniaUaEntity_Table.Region eq llaveUa.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona eq llaveUa.codigoZona.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Seccion eq llaveUa.codigoSeccion.guionSiVacioONull())
            orderBy (CampaniaUaEntity_Table.Orden.asc()))
        return query.queryList()
    }

    fun obtenerPenultimasCampaniasSincrono(llaveUa: LlaveUA, limite: Int): List<CampaniaUaEntity> {
        val query = (select
            from CampaniaUaEntity::class
            where (CampaniaUaEntity_Table.Region eq llaveUa.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona eq llaveUa.codigoZona.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Seccion eq llaveUa.codigoSeccion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Orden notEq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL)
            orderBy (CampaniaUaEntity_Table.Orden.asc())
            limit (limite))
        return query.queryList()
    }

    fun saveCampanias(data: CampaniaDataResponse?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply { campaniasUaList.deleteAndInsert() }
            db.setTransactionSuccessful()
            db.endTransaction()
            SyncType.FullyUpdated()
        } catch (ex: Exception) {
            db.endTransaction()
            throw SyncInsertDataException()
        }
    }

}

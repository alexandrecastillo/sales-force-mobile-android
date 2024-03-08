package biz.belcorp.salesforce.modules.inspires.core.data.repository.sync

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.save
import biz.belcorp.salesforce.modules.inspires.core.data.repository.sync.dto.DataResponse
import com.raizlabs.android.dbflow.config.FlowManager

class SyncDataStore {

    fun updateOrInsert(data: DataResponse?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                indicadorInspira?.save()
                rankingInspira.save()
                avancesInspira.save()
                condicionesInspira.save()
                condicionesLeyendaInspira.save()
                condicionesLeyendaDetalleInspira.save()
                avancesPeriodoInspira.save()
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            SyncType.PartiallyUpdated()
        } catch (ex: Exception) {
            db.endTransaction()
            throw SyncInsertDataException()
        }
    }

    fun deleteAndInsert(data: DataResponse?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                indicadorInspira?.deleteAndInsert()
                rankingInspira.deleteAndInsert()
                avancesInspira.deleteAndInsert()
                condicionesInspira.deleteAndInsert()
                condicionesLeyendaInspira.deleteAndInsert()
                condicionesLeyendaDetalleInspira?.deleteAndInsert()
                avancesPeriodoInspira.deleteAndInsert()
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            SyncType.FullyUpdated()
        } catch (ex: Exception) {
            db.endTransaction()
            throw SyncInsertDataException()
        }
    }

}

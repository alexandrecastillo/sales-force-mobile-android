package biz.belcorp.salesforce.modules.consultants.core.data.repository.sync

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.save
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto.SyncResponse
import com.raizlabs.android.dbflow.config.FlowManager

class SyncDataStore {

    fun updateOrInsert(data: SyncResponse.Resultado.Data?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                consultorasPdVPosibleCambioNivel.save()
                consultorasNivelCierrePeriodo.save()
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            SyncType.PartiallyUpdated()
        } catch (ex: Exception) {
            db.endTransaction()
            throw SyncInsertDataException()
        }
    }

    fun deleteAndInsert(data: SyncResponse.Resultado.Data?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                consultorasPdVPosibleCambioNivel.deleteAndInsert()
                consultorasNivelCierrePeriodo.deleteAndInsert()
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

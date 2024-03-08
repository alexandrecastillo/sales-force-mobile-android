package biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.birghtpath

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync.SyncResponse
import com.raizlabs.android.dbflow.config.FlowManager
import java.lang.Exception

class BrightPathIndicatorSyncDataStore {

    fun deleteAndInsert(data: SyncResponse.Result.Data?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }

        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                indicadorCambioNivel.deleteAndInsert()
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

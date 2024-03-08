package biz.belcorp.salesforce.modules.postulants.core.data.repository.sync

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.dto.DataResponse
import com.raizlabs.android.dbflow.config.FlowManager


internal class SyncPostulantesDataStore {

    fun deleteAndInsert(data: DataResponse?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                listadoParametrosUnete.deleteAndInsert()
                listadoTablaLogica.deleteAndInsert()
                uneteConfiguracion.deleteAndInsert()
                listadoTipoMeta.deleteAndInsert()
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

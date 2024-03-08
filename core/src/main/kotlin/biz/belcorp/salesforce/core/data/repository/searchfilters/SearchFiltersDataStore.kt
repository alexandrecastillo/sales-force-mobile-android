package biz.belcorp.salesforce.core.data.repository.searchfilters

import biz.belcorp.salesforce.core.data.repository.searchfilters.dto.SearchFiltersDataResponse
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import com.raizlabs.android.dbflow.config.FlowManager


internal class SearchFiltersDataStore {

    fun deleteAndInsert(data: SearchFiltersDataResponse?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                tipoEstadoList.deleteAndInsert()
                tipoSegmentoList.deleteAndInsert()
                tipoPedidoList.deleteAndInsert()
                tipoSaldoList.deleteAndInsert()
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

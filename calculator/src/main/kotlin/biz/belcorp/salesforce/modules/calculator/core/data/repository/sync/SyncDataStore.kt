package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.calculator.PartnerVariableEntity
import biz.belcorp.salesforce.core.utils.deleteAll
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.save
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.dto.SyncResponse
import com.raizlabs.android.dbflow.config.FlowManager

class SyncDataStore {

    fun updateOrInsert(data: SyncResponse.Resultado.Data?): SyncType {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                nivelSuperiorSE.save()
                parametroNivel.save()
                variableSocia?.save()
                bonoSocia.save()
                detallePagoTabla.save()
                detalleConcursoSE.save()
                resultadoCalculadora?.save()
                variablesMontoFijo.save()
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
                nivelSuperiorSE.deleteAndInsert()
                parametroNivel.deleteAndInsert()
                variableSocia?.also { it.deleteAndInsert() } ?: PartnerVariableEntity().deleteAll()
                bonoSocia.deleteAndInsert()
                detallePagoTabla.deleteAndInsert()
                detalleConcursoSE.deleteAndInsert()
                resultadoCalculadora?.deleteAndInsert()
                variablesMontoFijo.deleteAndInsert()
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

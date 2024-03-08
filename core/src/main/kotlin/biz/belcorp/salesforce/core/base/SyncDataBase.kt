package biz.belcorp.salesforce.core.base

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences

abstract class SyncOnDemandRepository(
    private val syncSharedPreferences: SyncSharedPreferences,
    private val type: SyncField
) {

    fun isAvailableToSync(forceSync: Boolean = false): Boolean {
        val defaultValue = NUMBER_ZERO_LONG
        val isDefaultValue = when (type) {
            SyncField.Consultants -> syncSharedPreferences.consultantsSyncDate == defaultValue
            SyncField.RejectedOrders -> syncSharedPreferences.rejectedOrdersSyncDate == defaultValue
            SyncField.CapitalizationKpi -> syncSharedPreferences.postulantKpiSyncData == defaultValue
        }
        return isDefaultValue || forceSync
    }

    fun updateSyncDate() {
        val time = System.currentTimeMillis()
        when (type) {
            SyncField.Consultants -> {
                syncSharedPreferences.consultantsSyncDate = time
                syncSharedPreferences.rejectedOrdersSyncDate = NUMBER_ZERO_LONG
                syncSharedPreferences.postulantKpiSyncData = NUMBER_ZERO_LONG
            }
            SyncField.RejectedOrders -> syncSharedPreferences.rejectedOrdersSyncDate = time
            SyncField.CapitalizationKpi -> syncSharedPreferences.postulantKpiSyncData = time
        }
    }

    fun getSyncDate(): Long {
        return when (type) {
            SyncField.Consultants -> {
                syncSharedPreferences.consultantsSyncDate
            }
            else -> NUMBER_ZERO_LONG
        }
    }

    sealed class SyncField {
        object Consultants : SyncField()
        object RejectedOrders : SyncField()
        object CapitalizationKpi : SyncField()
    }

}

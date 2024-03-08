package biz.belcorp.salesforce.core.data.preferences

import android.content.SharedPreferences
import biz.belcorp.salesforce.core.utils.*

class SyncSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "SyncPreferences"

        const val KEY_CYCLE_SYNC_CAMPAIGNS_MINUTES = "cycle_sync_campaigns_minutes"

        const val KEY_BASE_SYNC_DATE = "baseSyncDate"
        const val KEY_RDD_SYNC_DATE = "rddSyncDate"
        const val KEY_LEGACY_CONSULTANTS_SYNC_DATE = "consultantsSyncDate-legacy"
        const val KEY_ALPHA_CONSULTANTS_SYNC_DATE = "consultantsSyncDate-alpha"
        const val KEY_ALPHA_REJECTED_ORDER_SYNC_DATE = "rejectedOrdersSyncDate-alpha"
        const val KEY_POSTULANTS_SYNC_DATE = "postulantsSyncDate"
        const val KEY_POSTULANTS_KPI_SYNC_DATE = "postulantsKpiSyncDate"
        const val KEY_PROFIT_SYNC_DATE = "profitSyncDate"
        const val KEY_EVENTS_SYNC_DATE = "KEY_EVENTS_SYNC_DATE"
        const val KEY_SEARCH_FILTERS_SYNC_DATE = "searchFiltersSyncDate"
        const val KEY_CAMPAIGNS_SYNC_DATE = "campaignsSyncDate"
        const val KEY_FLAG_SYNC_EVENTS = "KEY_FLAG_SYNC_EVENTS"

        const val DEFAULT_CYCLE_SYNC_CAMPAIGNS_MINUTES = 1440

    }

    var cycleSyncCampaignsMinutes: Int
        get() = preferences.getIntNotZero(
            KEY_CYCLE_SYNC_CAMPAIGNS_MINUTES,
            DEFAULT_CYCLE_SYNC_CAMPAIGNS_MINUTES
        )
        set(value) = preferences.putInt(
            KEY_CYCLE_SYNC_CAMPAIGNS_MINUTES,
            value
        )

    var baseSyncDate: Long
        get() = preferences.getLong(KEY_BASE_SYNC_DATE)
        set(value) = preferences.putLong(KEY_BASE_SYNC_DATE, value)

    var consultantsSyncDate: Long
        get() = preferences.getLong(KEY_ALPHA_CONSULTANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_ALPHA_CONSULTANTS_SYNC_DATE, value)

    var rejectedOrdersSyncDate: Long
        get() = preferences.getLong(KEY_ALPHA_REJECTED_ORDER_SYNC_DATE)
        set(value) = preferences.putLong(KEY_ALPHA_REJECTED_ORDER_SYNC_DATE, value)

    var legacyConsultantsSyncDate: Long
        get() = preferences.getLong(KEY_ALPHA_CONSULTANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_LEGACY_CONSULTANTS_SYNC_DATE, value)

    var legacyCalculatorSyncDate: Long
        get() = preferences.getLong(KEY_ALPHA_CONSULTANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_LEGACY_CONSULTANTS_SYNC_DATE, value)

    var legacyInspiresSyncDate: Long
        get() = preferences.getLong(KEY_ALPHA_CONSULTANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_LEGACY_CONSULTANTS_SYNC_DATE, value)

    var postulantsSyncDate: Long
        get() = preferences.getLong(KEY_POSTULANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_POSTULANTS_SYNC_DATE, value)

    var postulantKpiSyncData: Long
        get() = preferences.getLong(KEY_POSTULANTS_KPI_SYNC_DATE)
        set(value) = preferences.putLong(KEY_POSTULANTS_KPI_SYNC_DATE, value)

    var profitSyncDate: Long
        get() = preferences.getLong(KEY_PROFIT_SYNC_DATE)
        set(value) = preferences.putLong(KEY_PROFIT_SYNC_DATE, value)

    var rddSyncDate: Long?
        get() = preferences.getLong(KEY_RDD_SYNC_DATE)
        set(value) = preferences.putLong(KEY_RDD_SYNC_DATE, value ?: 0)

    var rddEventsSyncDate: Long?
        get() = preferences.getLong(KEY_EVENTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_EVENTS_SYNC_DATE, value ?: 0)

    var flagEventosSyncEnProceso: Boolean
        get() = preferences.getBoolean(KEY_FLAG_SYNC_EVENTS)
        set(value) = preferences.putBoolean(KEY_FLAG_SYNC_EVENTS, value)

    var searchFiltersSyncDate: Long
        get() = preferences.getLong(KEY_SEARCH_FILTERS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_SEARCH_FILTERS_SYNC_DATE, value)

    var campaignsSyncDate: Long
        get() = preferences.getLong(KEY_CAMPAIGNS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_CAMPAIGNS_SYNC_DATE, value)

    fun clear() {
        baseSyncDate = 0L
        consultantsSyncDate = 0L
        rejectedOrdersSyncDate = 0L
        legacyConsultantsSyncDate = 0L
        legacyCalculatorSyncDate = 0L
        legacyInspiresSyncDate = 0L
        postulantsSyncDate = 0L
        postulantKpiSyncData = 0L
        profitSyncDate = 0L
        rddSyncDate = 0L
        rddEventsSyncDate = 0L
        flagEventosSyncEnProceso = false
        searchFiltersSyncDate = 0L
        campaignsSyncDate = 0L
    }

}

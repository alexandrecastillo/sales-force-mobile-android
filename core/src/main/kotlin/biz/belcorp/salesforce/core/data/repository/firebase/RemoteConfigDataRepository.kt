package biz.belcorp.salesforce.core.data.repository.firebase

import biz.belcorp.salesforce.core.constants.Constant.UNDERSCORE
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences.Companion.FLAG_CALCULATOR
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences.Companion.FLAG_HIDE_POSSIBLE_CHANGES
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences.Companion.KEY_CACHE_MAX_AGE
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences.Companion.KEY_CACHE_MAX_STALE
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences.Companion.KEY_UPDATE_TYPE
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences.Companion.KEY_ONLINESTORE_ENABLED
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences.Companion.KEY_ONLINESTORE_TITLE
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences.Companion.KEY_ONLINESTORE_TYPE
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences.Companion.KEY_CYCLE_SYNC_CAMPAIGNS_MINUTES
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.domain.repository.firebase.RemoteConfigRepository
import biz.belcorp.salesforce.core.utils.AppBuildConfig
import biz.belcorp.salesforce.core.utils.isNotNull
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RemoteConfigDataRepository(
    private val frc: FirebaseRemoteConfig,
    private val syncPreferences: SyncSharedPreferences,
    private val configPreferences: ConfigSharedPreferences,
    private val featuresPreferences: FeaturesSharedPreferences,
    private val userPreferences: UserSharedPreferences
) : RemoteConfigRepository {

    override suspend fun fetchConfig() {
        fetchAndActivate()
        saveConfig()
        saveFeatures()
    }

    private fun saveConfig() {
        configPreferences.maxAge = frc.getLong(getKey(key = KEY_CACHE_MAX_AGE))
        configPreferences.maxStale = frc.getLong(getKey(key = KEY_CACHE_MAX_STALE))
        configPreferences.updateType =
            frc.getString(getKey(userPreferences.codPais, key = KEY_UPDATE_TYPE))
        syncPreferences.cycleSyncCampaignsMinutes =
            frc.getLong(getKey(key = KEY_CYCLE_SYNC_CAMPAIGNS_MINUTES)).toInt()
        configPreferences.flagCalculator =
            frc.getBoolean(getKey(userPreferences.codPais, key = FLAG_CALCULATOR))
        configPreferences.flagHidePossibleChanges =
            frc.getBoolean(getKey(userPreferences.codPais, key = FLAG_HIDE_POSSIBLE_CHANGES))
    }

    private fun saveFeatures() {
        featuresPreferences.onlineStoreEnabled =
            frc.getBoolean(getKey(userPreferences.codPais, key = KEY_ONLINESTORE_ENABLED))
        featuresPreferences.onlineStoreTitle =
            frc.getString(getKey(userPreferences.codPais, key = KEY_ONLINESTORE_TITLE))
        frc.getString(getKey(userPreferences.codPais, key = KEY_ONLINESTORE_TITLE))
        featuresPreferences.onlineStoreType =
            frc.getString(getKey(userPreferences.codPais, key = KEY_ONLINESTORE_TYPE))
        configPreferences.updateType =
            frc.getString(getKey(userPreferences.codPais, key = KEY_UPDATE_TYPE))
    }

    private fun getKey(countryIso: String? = null, key: String): String {
        val env = AppBuildConfig.getEnviroment()
        val countryParam = "$UNDERSCORE$countryIso".takeIf { countryIso.isNotNull() }.orEmpty()
        return "$env$countryParam$UNDERSCORE$key".toLowerCase(Locale.getDefault())
    }

    private suspend fun fetchAndActivate() = suspendCancellableCoroutine<Boolean> { cont ->
        frc.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cont.resume(true)
                } else {
                    cont.resumeWithException(task.exception ?: Throwable())
                }
            }
    }

}

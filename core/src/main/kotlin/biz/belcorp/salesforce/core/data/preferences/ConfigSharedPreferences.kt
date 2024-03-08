package biz.belcorp.salesforce.core.data.preferences

import android.content.SharedPreferences
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.*

class ConfigSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "ConfigPreferences"

        const val KEY_CACHE_MAX_AGE = "cache_max_age"
        const val KEY_CACHE_MAX_STALE = "cache_max_stale"
        const val KEY_UPDATE_TYPE = "update_type"

        const val DEFAULT_CACHE_MAX_AGE = 5L
        const val DEFAULT_CACHE_MAX_STALE = 86400L
        const val DEFAULT_UPDATE_TYPE = "NONE"

        const val DEVICE_FCM_TOKEN = "device_fcm_token"
        const val DEVICE_FCM_TOKEN_PENDING = "device_fcm_token_pending"
        const val DEVICE_LEADER_FCM_TOPIC = "device_leader_fcm_topic"

        const val DEVICE_ID = "device_id"

        const val FLAG_CALCULATOR = "flag_calculator"
        const val FLAG_HIDE_POSSIBLE_CHANGES = "hide_possible_changes"

    }

    var maxAge: Long?
        get() = preferences.getLong(KEY_CACHE_MAX_AGE, DEFAULT_CACHE_MAX_AGE)
        set(value) = preferences.putLong(KEY_CACHE_MAX_AGE, value ?: 0)

    var maxStale: Long?
        get() = preferences.getLong(KEY_CACHE_MAX_STALE, DEFAULT_CACHE_MAX_STALE)
        set(value) = preferences.putLong(KEY_CACHE_MAX_STALE, value ?: 0)

    var fcmToken: String?
        get() = preferences.getString(DEVICE_FCM_TOKEN, Constant.EMPTY_STRING)
        set(value) = preferences.putString(DEVICE_FCM_TOKEN, value)

    var fcmTokenPending: Boolean
        get() = preferences.getBoolean(DEVICE_FCM_TOKEN_PENDING)
        set(value) = preferences.putBoolean(DEVICE_FCM_TOKEN_PENDING, value)

    var leaderFcmTopic: String?
        get() = preferences.getString(DEVICE_LEADER_FCM_TOPIC, Constant.EMPTY_STRING)
        set(value) = preferences.putString(DEVICE_LEADER_FCM_TOPIC, value)

    var deviceId: Long
        get() = preferences.getLong(DEVICE_ID)
        set(value) = preferences.putLong(DEVICE_ID, value)

    var updateType: String?
        get() = preferences.getString(KEY_UPDATE_TYPE, DEFAULT_UPDATE_TYPE)
        set(value) = preferences.putString(KEY_UPDATE_TYPE, value)

    var flagCalculator: Boolean?
        get() = preferences.getBoolean(FLAG_CALCULATOR)
        set(value) = preferences.putBoolean(FLAG_CALCULATOR, value ?: false)

    var flagHidePossibleChanges: Boolean?
        get() = preferences.getBoolean(FLAG_HIDE_POSSIBLE_CHANGES)
        set(value) = preferences.putBoolean(FLAG_HIDE_POSSIBLE_CHANGES, value ?: false)

}

package biz.belcorp.salesforce.core.data.preferences

import android.content.SharedPreferences
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.*

class AuthSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "AuthPreferences"

        const val KEY_TOKEN = "token"
        const val KEY_TOKEN_EXPIRES = "tokenExpires"

        const val KEY_LEGACY_TOKEN = "legacyToken"
        const val KEY_LEGACY_REFRESH_TOKEN = "legacyrefreshToken"

        const val KEY_SB_TOKEN = "sbToken"

        const val KEY_IS_LOGGED = "is_logged"
        const val KEY_LOGIN_IN_PROGRESS = "loginInProgress"

        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"

        const val DEVICE_SB_TOKEN = "device_sb_token"
        const val DEVICE_SB_TOKEN_EXPIRES = "device_sb_token_expires"

    }

    var logged: Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED)
        set(value) = preferences.putBoolean(KEY_IS_LOGGED, value)

    var loginInProgress: Boolean
        get() = preferences.getBoolean(KEY_LOGIN_IN_PROGRESS)
        set(value) = preferences.putBoolean(KEY_LOGIN_IN_PROGRESS, value)

    var token: String?
        get() = preferences.getString(KEY_TOKEN, "")
        set(value) = preferences.putString(KEY_TOKEN, value)

    var tokenExpires: Long
        get() = preferences.getLong(KEY_TOKEN_EXPIRES)
        set(value) = preferences.putLong(KEY_TOKEN_EXPIRES, value)

    var legacyToken: String?
        get() = preferences.getString(KEY_LEGACY_TOKEN, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_LEGACY_TOKEN, value)

    var legacyRefreshToken: String?
        get() = preferences.getString(KEY_LEGACY_REFRESH_TOKEN, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_LEGACY_REFRESH_TOKEN, value)

    var sbToken: String?
        get() = preferences.getString(KEY_SB_TOKEN, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_SB_TOKEN, value)

    var username: String?
        get() = preferences.getString(KEY_USERNAME, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_USERNAME, value)

    var password: String?
        get() = preferences.getString(KEY_PASSWORD, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_PASSWORD, value)

    var deviceSBToken: String?
        get() = preferences.getString(DEVICE_SB_TOKEN)
        set(value) = preferences.putString(DEVICE_SB_TOKEN, value)

    var deviceSBTokenExpires: Long
        get() = preferences.getLong(DEVICE_SB_TOKEN_EXPIRES)
        set(value) = preferences.putLong(DEVICE_SB_TOKEN_EXPIRES, value)

    fun clear() {

        logged = false
        loginInProgress = false
        token = Constant.EMPTY_STRING
        tokenExpires = 0L
        legacyToken = Constant.EMPTY_STRING
        legacyRefreshToken = Constant.EMPTY_STRING
        sbToken = Constant.EMPTY_STRING
        username = Constant.EMPTY_STRING
        password = Constant.EMPTY_STRING

    }
}

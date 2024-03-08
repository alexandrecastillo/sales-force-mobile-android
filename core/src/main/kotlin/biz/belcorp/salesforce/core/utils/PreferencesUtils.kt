package biz.belcorp.salesforce.core.utils

import android.content.SharedPreferences
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO


fun SharedPreferences.getString(key: String): String? {
    return getString(key, null)
}

fun SharedPreferences.putString(key: String, value: String?) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.getBoolean(key: String): Boolean {
    return getBoolean(key, false)
}

fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    edit().putBoolean(key, value).apply()
}

fun SharedPreferences.getLong(key: String): Long {
    return getLong(key, 0)
}

fun SharedPreferences.putLong(key: String, value: Long) {
    edit().putLong(key, value).apply()
}

fun SharedPreferences.getIntNotZero(key: String, defValue: Int): Int {
    return getInt(key, NUMBER_ZERO).run {
        if (this == NUMBER_ZERO) return defValue
        else this
    }
}

fun SharedPreferences.putInt(key: String, value: Int) {
    edit().putInt(key, value).apply()
}

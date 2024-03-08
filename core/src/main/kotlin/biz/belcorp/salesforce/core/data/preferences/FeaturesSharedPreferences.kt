package biz.belcorp.salesforce.core.data.preferences

import android.content.SharedPreferences
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.*

class FeaturesSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "FeaturesPreferences"

        const val KEY_ONLINESTORE_ENABLED = "onlinestore_enabled"
        const val KEY_ONLINESTORE_TITLE = "onlinestore_title"
        const val KEY_ONLINESTORE_TYPE = "onlinestore_type"

        const val DEFAULT_ONLINESTORE_ENABLED = false
        const val DEFAULT_ONLINESTORE_TITLE = "Mi Tienda Online"
        const val DEFAULT_ONLINESTORE_TYPE = "ONLINE_STORE"

    }

    var onlineStoreEnabled: Boolean
        get() = preferences.getBoolean(KEY_ONLINESTORE_ENABLED, DEFAULT_ONLINESTORE_ENABLED)
        set(value) = preferences.putBoolean(KEY_ONLINESTORE_ENABLED, value)

    var onlineStoreTitle: String
        get() = preferences.getString(KEY_ONLINESTORE_TITLE, Constant.EMPTY_STRING) ?: DEFAULT_ONLINESTORE_TITLE
        set(value) = preferences.putString(KEY_ONLINESTORE_TITLE, value)

    var onlineStoreType: String
        get() = preferences.getString(KEY_ONLINESTORE_TYPE, Constant.EMPTY_STRING) ?: DEFAULT_ONLINESTORE_TYPE
        set(value) = preferences.putString(KEY_ONLINESTORE_TYPE, value)

}

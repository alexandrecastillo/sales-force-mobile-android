package biz.belcorp.salesforce.core.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.util.ArrayList

fun <T : Parcelable> Bundle.getParcelableArrayListCompat(key: String, clazz: Class<out T>): ArrayList<T>? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableArrayList(key, clazz)
        } else {
            getParcelableArrayList(key)
        }
    } catch (e: Exception) {
        null
    }
}

package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.BuildConfig.DEBUG

fun <T> T.safeAlso(f: (T) -> Unit): T? {
    return try {
        f.invoke(this)
        this
    } catch (e: Exception) {
        if (DEBUG) e.printStackTrace()
        null
    }
}

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T = apply {
    if (condition) block(this)
}

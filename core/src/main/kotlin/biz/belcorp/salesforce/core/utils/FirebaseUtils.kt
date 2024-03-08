package biz.belcorp.salesforce.core.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics


fun getCrashlyticsInstance(): FirebaseCrashlytics? {
    return try {
        FirebaseCrashlytics.getInstance()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

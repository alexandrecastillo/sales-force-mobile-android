package biz.belcorp.salesforce.core.gps

import android.content.Intent

interface RequestListenerGPS {
    fun notifyRequestGPS(requestCode: Int, resultCode: Int, data: Intent)
}

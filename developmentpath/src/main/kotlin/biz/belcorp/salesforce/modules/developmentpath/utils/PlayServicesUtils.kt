package biz.belcorp.salesforce.modules.developmentpath.utils

import android.app.Activity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

object PlayServicesUtils {

    private const val REQUEST_PLAY_SERVICES_RESOLUTION = 100

    @JvmStatic
    fun checkPlayServices(activity: Activity): Boolean {
        val gaa = GoogleApiAvailability.getInstance()
        val result = gaa.isGooglePlayServicesAvailable(activity.applicationContext)
        if (result != ConnectionResult.SUCCESS) {
            if (gaa.isUserResolvableError(result)) {
                gaa.getErrorDialog(activity, result,
                    REQUEST_PLAY_SERVICES_RESOLUTION
                ).show()
            }
            return false
        }
        return true
    }

}

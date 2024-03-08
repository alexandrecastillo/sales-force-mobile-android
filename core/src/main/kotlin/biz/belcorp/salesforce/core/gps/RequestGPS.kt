package biz.belcorp.salesforce.core.gps

import android.app.Activity
import android.content.IntentSender
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

class RequestGPS {

    fun requestGPS(activity: Activity) {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 30 * 1000
        locationRequest.fastestInterval = 20 * 1000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).setAlwaysShow(true)
        val task = LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())

        task.addOnCompleteListener { }
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(
                        activity,
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

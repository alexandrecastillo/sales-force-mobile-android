package biz.belcorp.salesforce.modules.postulants.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager

class LocationUtil {

    companion object {

        @JvmStatic
        fun lastKnownLocation(context: Context): Location? {
            var bestLocation: Location? = null
            val mLocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = mLocationManager.getProviders(true)

            for (provider in providers) {

                val l = mLocationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                    // Found best last known location: %s", l);
                    bestLocation = l
                }
            }

            return bestLocation
        }

    }

}


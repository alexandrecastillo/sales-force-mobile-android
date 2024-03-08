package biz.belcorp.salesforce.core.features.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionsUtil(private val context: Context) {

    companion object {
        @JvmField
        val REQUEST_CALL_ID = 51
        @JvmField
        val LOCATION_PERMISSION = 54
        @JvmField
        val FINE_LOCATION = 55
    }


    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isFineLocationPermissionGranted(): Boolean {
        return checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

}

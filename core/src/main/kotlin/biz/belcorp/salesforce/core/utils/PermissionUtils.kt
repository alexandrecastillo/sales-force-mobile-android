package biz.belcorp.salesforce.core.utils

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

fun Activity.isFineLocationPermissionGranted(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.isAccessCoarseLocationPermissionGranted(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.isCallPhonePermissionGranted(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        CALL_PHONE
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.isWriteExternalStoragePermissionGranted(): Boolean {
    return permissionsWriteStorage().all {
        ActivityCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

fun isPermissionGranted(permissionCode: Int) = permissionCode == PackageManager.PERMISSION_GRANTED

private fun permissionsWriteStorage(): Array<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            READ_MEDIA_AUDIO,
            READ_MEDIA_IMAGES,
            READ_MEDIA_AUDIO
        )
    } else {
        arrayOf(
            WRITE_EXTERNAL_STORAGE
        )
    }
}

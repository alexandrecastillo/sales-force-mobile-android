package biz.belcorp.salesforce.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import biz.belcorp.salesforce.core.constants.Constant
import java.net.NetworkInterface
import java.util.*


class DeviceUtil {

    companion object {
        @JvmStatic
        fun hasGPS(context: Context): Boolean {
            var hasGPS = false
            context.getSystemService(Context.LOCATION_SERVICE)?.let {
                (it as LocationManager).allProviders?.let {
                    hasGPS = it.contains(LocationManager.GPS_PROVIDER)
                }
            }
            return hasGPS
        }


        /*
         * Get IP address from first non-localhost interface
         * @param useIPv4  true=return ipv4, false=return ipv6
         * @return  address or empty string
         */
        fun getIPAddress(useIPv4: Boolean): String {
            try {
                val interfaces: ArrayList<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())

                interfaces.forEach { intfc ->
                    val addrs = Collections.list(intfc.inetAddresses)

                    addrs.forEach { addr ->
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress
                            val isIPv4 = sAddr.indexOf(':') < 0
                            if (useIPv4) {
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') //drop ip6 zone suffix
                                    return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(
                                        0,
                                        delim
                                    ).toUpperCase()
                                }
                            }
                        }
                    }
                }

            } catch (ex: Exception) {
                Log.i("NetworkInterface", ex.message.orEmpty())
            }
            return ""
        }

        @SuppressLint("MissingPermission")
        fun getDeviceIMEI(context: Context?): String {

            try {
                val telephonyManager =
                    context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                if (ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return ""
                }
                val imei = telephonyManager.deviceId
                return if (imei != null && !imei.isEmpty()) {
                    imei
                } else {
                    Build.SERIAL
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return "not_found"
        }

        fun getVersionCode(): String? {
            return Build.VERSION.RELEASE.toString()
        }

        fun getVersionSDK(): String? {
            return Build.VERSION.SDK_INT.toString()
        }

        fun getModel(): String? {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) model else "$manufacturer $model"
        }

        var uniqueID: String = ""
        var PREF_UNIQUE_ID: String = "JEJEJ"

        fun getId(context: Context): String? {
            var androidID = try {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (ex: Exception) {
                Constant.EMPTY_STRING
            }

            if (TextUtils.isEmpty(androidID)) {
                androidID = getUUID(context)
            }

            return androidID
        }

        @Synchronized
        private fun getUUID(context: Context): String? {

            if (uniqueID == null) {
                val sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE)
                uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, Constant.EMPTY_STRING)
                    ?: Constant.EMPTY_STRING

                if (uniqueID == null) {
                    uniqueID = UUID.randomUUID().toString()
                    val editor = sharedPrefs.edit()
                    editor.putString(PREF_UNIQUE_ID, uniqueID)
                    editor.apply()
                }
            }
            return uniqueID
        }
    }
}

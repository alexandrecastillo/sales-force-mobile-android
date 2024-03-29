package biz.belcorp.salesforce.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
private fun postAndroidMInternetCheck(
    connectivityManager: ConnectivityManager
): Boolean {
    val network = connectivityManager.activeNetwork
    val connection =
        connectivityManager.getNetworkCapabilities(network)
    return connection != null && (
        connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
}

@Suppress("DEPRECATION")
private fun preAndroidMInternetCheck(
    connectivityManager: ConnectivityManager
): Boolean {
    val activeNetwork = connectivityManager.activeNetworkInfo
    if (activeNetwork != null) {
        @Suppress("DEPRECATION")
        return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
            activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
    }
    return false
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        postAndroidMInternetCheck(connectivityManager)
    } else {
        preAndroidMInternetCheck(connectivityManager)
    }
}

@file:Suppress("DEPRECATION")

package biz.belcorp.salesforce.core.connectivity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import biz.belcorp.salesforce.core.utils.AppBuildConfig

class ConnectivityLiveData(private val connectivityManager: ConnectivityManager) :
    LiveData<ConnectivityState>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(ConnectivityState.Online)
        }

        override fun onLost(network: Network) {
            postValue(ConnectivityState.Offline)
        }
    }

    @SuppressLint("NewApi")
    override fun onActive() {
        super.onActive()

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(getState(activeNetwork?.isConnectedOrConnecting == true))

        if (AppBuildConfig.getBuildVersion() >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    private fun getState(state: Boolean): ConnectivityState {
        return if (state) {
            ConnectivityState.Online
        } else {
            ConnectivityState.Offline
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    companion object {

        fun init(application: Application): ConnectivityLiveData {
            val connectivityManager = application
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return ConnectivityLiveData(connectivityManager)
        }

    }

}

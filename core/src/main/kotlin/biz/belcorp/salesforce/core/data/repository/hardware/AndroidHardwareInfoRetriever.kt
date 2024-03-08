package biz.belcorp.salesforce.core.data.repository.hardware

import android.content.Context
import android.net.ConnectivityManager
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.utils.toUpperCase
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfo
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfoRetriever
import biz.belcorp.salesforce.core.domain.entities.hardware.NetworkStatus

class AndroidHardwareInfoRetriever constructor(private val context: Context) : HardwareInfoRetriever {

    override fun get(): HardwareInfo {
        return HardwareInfo(buildVariant = getBuildVariant(),
                currentNetworkStatus = getNetworkStatus())
    }

    private fun getNetworkStatus(): NetworkStatus {
        val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
            NetworkStatus.CONNECTED
        } else {
            NetworkStatus.DISCONNECTED
        }
    }

    private fun getBuildVariant(): BuildVariant {
        val buildType = BuildConfig.BUILD_TYPE.toUpperCase()
        val ambiente = BuildVariant.values().find { it.name == buildType }
        return ambiente ?: BuildVariant.DEVELOP
    }
}

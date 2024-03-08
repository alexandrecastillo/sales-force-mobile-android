package biz.belcorp.salesforce.core.dynamic

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest


class SplitInstallLiveData(
    private val splitInstallManager: SplitInstallManager
) : LiveData<InstallState>() {

    private var mySessionId = 0

    fun install(features: List<String>) = apply {
        val modules = getModulesToInstall(features)
        if (modules.isNotEmpty()) {
            val request = getSplitInstallRequest(modules)
            splitInstallManager.install(::onStateChanged, request)
        } else {
            value = InstallState.Installed
        }
    }

    private fun onStateChanged(state: InstallState) {
        if (state is InstallState.Success) {
            mySessionId = state.id
        }
        value = state
    }

    private fun getInstalledModules(): Set<String> {
        return splitInstallManager.installedModules
    }

    private fun getModulesToInstall(feature: List<String>): List<String> {
        return feature.filter { it !in getInstalledModules() }
    }

    private fun getSplitInstallRequest(modulesToInstall: List<String>): SplitInstallRequest {
        return SplitInstallRequest.newBuilder()
            .addModules(modulesToInstall)
            .build()
    }

    override fun removeObserver(observer: Observer<in InstallState>) {
        splitInstallManager.cancelInstall(mySessionId)
        super.removeObserver(observer)
    }

}

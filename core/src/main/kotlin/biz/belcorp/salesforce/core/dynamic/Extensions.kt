package biz.belcorp.salesforce.core.dynamic

import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*

internal fun SplitInstallRequest.Builder.addModules(feature: List<String>) = apply {
    feature.forEach { addModule(it) }
}

internal fun SplitInstallManager.install(
    state: (InstallState) -> Unit,
    request: SplitInstallRequest
) {
    var mySessionId = 0
    registerListener(SplitInstallStateUpdatedListener { installState ->
        if (installState.status() == FAILED
            && installState.errorCode() == SERVICE_DIED
        ) {
            state.invoke(InstallState.Retry(GENERIC_ERROR_MESSAGE))
            return@SplitInstallStateUpdatedListener
        }
        if (installState.sessionId() == mySessionId) {
            when (installState.status()) {
                DOWNLOADING -> {
                    val totalBytes = installState.totalBytesToDownload()
                    val progress = installState.bytesDownloaded()
                    state.invoke(InstallState.Downloading(progress, totalBytes))
                }
                INSTALLED -> {
                    state.invoke(InstallState.Installed)
                }
            }
        }
    })
    startInstall(request)
        ?.addOnSuccessListener {
            mySessionId = it
            state.invoke(InstallState.Success(it))
        }
        ?.addOnFailureListener {
            state.invoke(handleException(it))
        }
}

internal fun handleException(exception: Exception): InstallState {
    return when (exception) {
        is SplitInstallException -> when (exception.errorCode) {
            NETWORK_ERROR -> InstallState.Failed(NETWORK_ERROR_MESSAGE)
            MODULE_UNAVAILABLE -> InstallState.Failed(MODULE_UNAVAILABLE_MESSAGE)
            else -> InstallState.Failed(GENERIC_ERROR_MESSAGE)
        }
        else -> InstallState.Failed(GENERIC_ERROR_MESSAGE)
    }
}

private const val NETWORK_ERROR_MESSAGE = "Error de conección"
private const val MODULE_UNAVAILABLE_MESSAGE = "Modulos no disponibles"
private const val GENERIC_ERROR_MESSAGE = "Ha ocurrido un error"

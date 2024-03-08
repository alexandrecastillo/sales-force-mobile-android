package biz.belcorp.salesforce.core.update

import android.app.Activity
import android.content.IntentSender
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.utils.Logger
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus.DOWNLOADED
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import com.google.android.play.core.tasks.OnFailureListener
import com.google.android.play.core.tasks.OnSuccessListener

class UpdateManagerLiveData(
    private val updateManager: AppUpdateManager
) : LiveData<UpdateState>(),
    InstallStateUpdatedListener,
    OnSuccessListener<AppUpdateInfo>,
    OnFailureListener {

    companion object {

        private const val REQUEST_CODE_UPDATE = 17362
        private const val UPDATE_FAILED = "Update Failed"
        private const val TAG = "UpdateManagerLiveData"

    }

    private var isUpdateDialogOpen = false
    private var updateType = FLEXIBLE
    private var updateInfo: AppUpdateInfo? = null

    fun init(@AppUpdateType type: Int) {
        updateType = type
    }

    fun startUpdate(activity: Activity) {
        try {
            if (isUpdateDialogOpen) return
            updateManager.startUpdateFlowForResult(
                updateInfo ?: return,
                updateType,
                activity,
                REQUEST_CODE_UPDATE
            )
            isUpdateDialogOpen = true
        } catch (e: IntentSender.SendIntentException) {
            onFailure(e)
        }
    }

    fun handleResult(requestCode: Int, resultCode: Int) {
        if (requestCode == REQUEST_CODE_UPDATE) {
            isUpdateDialogOpen = false
            when (resultCode) {
                Activity.RESULT_CANCELED -> restartIfNeeded()
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> onFailure(Exception(UPDATE_FAILED))
            }
        }
    }

    fun completeUpdate() {
        updateManager.completeUpdate()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in UpdateState>) {
        super.observe(owner, observer)
        setupListeners()
    }

    private fun setupListeners() {
        updateManager.registerListener(this)
        updateManager.appUpdateInfo.addOnSuccessListener(this)
        updateManager.appUpdateInfo.addOnFailureListener(this)
    }

    private fun restartIfNeeded() {
        if (updateType == IMMEDIATE) {
            updateManager.appUpdateInfo.addOnSuccessListener(this)
        }
    }

    override fun onStateUpdate(state: InstallState?) {
        if (state?.installStatus() == DOWNLOADED) onUpdateDownloaded()
    }

    override fun onSuccess(info: AppUpdateInfo?) {
        when {
            info?.installStatus() == DOWNLOADED -> onUpdateDownloaded()
            info?.updateAvailability() == UPDATE_AVAILABLE ||
            info?.updateAvailability() == DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> onUpdateAvailable(info)
        }
    }

    private fun onUpdateAvailable(info: AppUpdateInfo) {
        val flexibleUpdateAllowed = updateType == FLEXIBLE && info.isUpdateTypeAllowed(FLEXIBLE)
        val inmediateUpdateAllowed = updateType == IMMEDIATE && info.isUpdateTypeAllowed(IMMEDIATE)
        if (flexibleUpdateAllowed || inmediateUpdateAllowed) {
            updateInfo = info
            value = UpdateState.UpdateAvailable
        }
    }

    private fun onUpdateDownloaded() {
        value = UpdateState.Downloaded
    }

    override fun onFailure(e: Exception?) {
        Logger.e(TAG, e?.message.orEmpty(), e ?: return)
    }

    override fun removeObserver(observer: Observer<in UpdateState>) {
        updateManager.unregisterListener(this)
        super.removeObserver(observer)
    }

}

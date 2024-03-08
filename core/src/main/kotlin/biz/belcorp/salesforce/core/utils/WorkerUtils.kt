package biz.belcorp.salesforce.core.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.*
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncWorker


val WorkInfo.State.isRunning get() = this == WorkInfo.State.RUNNING

data class WorkerParams(
    val uid: String,
    val data: Data.Builder? = null
)

inline fun <reified T : CoroutineWorker> Context.startWorker(params: WorkerParams?) {
    WorkManager.getInstance(this)
        .beginUniqueWork(
            params?.uid ?: T::class.java.name,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            buildRequest<T>(params?.data)
        )
        .enqueue()
}

inline fun <reified T : CoroutineWorker> Fragment.cancelWorker(uid: String? = null) {
    context?.also { it.cancelWorker<T>(uid) }
}

inline fun <reified T : CoroutineWorker> Context.cancelWorker(uid: String? = null) {
    WorkManager.getInstance(this)
        .cancelUniqueWork(uid ?: T::class.java.name)
}

inline fun <reified T : CoroutineWorker> buildRequest(data: Data.Builder?): OneTimeWorkRequest {

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    return OneTimeWorkRequest.Builder(T::class.java)
        .setConstraints(constraints)
        .setData(data)
        .build()
}

inline fun <reified T> Fragment.observeWorkState(f: OnWorkerStatusChanged.() -> Unit) {
    val onStatusChanged = OnWorkerStatusChanged().apply { f.invoke(this) }
    WorkManager.getInstance(requireContext())
        .getWorkInfosForUniqueWorkLiveData(T::class.java.name)
        .observe(viewLifecycleOwner, onStatusChanged.workerStatusObserver)
}

inline fun <reified T> AppCompatActivity.observeWorkState(f: OnWorkerStatusChanged.() -> Unit) {
    val onStatusChanged = OnWorkerStatusChanged().apply { f.invoke(this) }
    WorkManager.getInstance(this)
        .getWorkInfosForUniqueWorkLiveData(T::class.java.name)
        .observe(this, onStatusChanged.workerStatusObserver)
}

class OnWorkerStatusChanged {

    var onRunning: (() -> Unit)? = null
    var onEnqueued: (() -> Unit)? = null
    var onSucceeded: (() -> Unit)? = null
    var onFailed: (() -> Unit)? = null
    var onBlocked: (() -> Unit)? = null
    var onCancelled: (() -> Unit)? = null
    var onChanged: ((WorkInfo.State) -> Unit)? = null

    val workerStatusObserver = Observer<List<WorkInfo>> {
        it.firstOrNull()?.apply {
            when (state) {
                WorkInfo.State.RUNNING -> onRunning?.invoke()
                WorkInfo.State.ENQUEUED -> onEnqueued?.invoke()
                WorkInfo.State.SUCCEEDED -> onSucceeded?.invoke()
                WorkInfo.State.FAILED -> onFailed?.invoke()
                WorkInfo.State.BLOCKED -> onBlocked?.invoke()
                WorkInfo.State.CANCELLED -> onCancelled?.invoke()
            }
            onChanged?.invoke(state)
        }
    }

}

fun OneTimeWorkRequest.Builder.setData(data: Data.Builder?) = apply {
    data?.also { setInputData(it.build()) }
}

/* For Sync */

fun Fragment.startSyncWorker(uid: String, group: SyncGroup, b: (Data.Builder.() -> Unit) ?= null) {
    context?.startSyncWorker(uid, group, b)
}

fun Context.startSyncWorker(uid: String, group: SyncGroup, b: (Data.Builder.() -> Unit) ?= null) {
    val data = Data.Builder().apply {
        putString(SyncGroup::class.java.simpleName, group.name)
        b?.invoke(this)
    }
    val params = WorkerParams(uid, data)
    startWorker<SyncWorker>(params)
}

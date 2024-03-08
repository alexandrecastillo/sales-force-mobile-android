package biz.belcorp.salesforce.modules.postulants.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.postulants"
private const val POSTULANTS_SYNC_WORKER_ID =
    "$WORKER_PREFIX.POSTULANTS_SYNC_WORKER"
private const val POSTULANTS_APPLICATIONS_SYNC_WORKER_ID =
    "$WORKER_PREFIX.POSTULANTS_APPLICATIONS_SYNC_WORKER"

fun Context.startModuleSyncWorker() {
    startSyncWorker(POSTULANTS_SYNC_WORKER_ID, SyncGroup.POSTULANTS)
}

fun Context.startApplicationsSyncWorker() {
    startSyncWorker(POSTULANTS_APPLICATIONS_SYNC_WORKER_ID, SyncGroup.POSTULANTS_APPLICATIONS)
}

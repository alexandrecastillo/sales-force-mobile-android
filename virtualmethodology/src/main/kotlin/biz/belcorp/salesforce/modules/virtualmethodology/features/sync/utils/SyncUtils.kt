package biz.belcorp.salesforce.modules.virtualmethodology.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.virtualmethodology"
private const val VIRTUAL_METHODOLOGY_SYNC_WORKER_ID = "$WORKER_PREFIX.VIRTUAL_METHODOLOGY_SYNC_WORKER"

fun Context.startModuleSyncWorker() {
    startSyncWorker(VIRTUAL_METHODOLOGY_SYNC_WORKER_ID, SyncGroup.VIRTUAL_METHODOLOGY)
}

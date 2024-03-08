package biz.belcorp.salesforce.modules.termsconditions.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.termsconditions"
private const val TERMS_CONDITIONS_SYNC_WORKER_ID = "$WORKER_PREFIX.TERMS_CONDITIONS_SYNC_WORKER"

fun Context.startTermsSyncWorker() {
    startSyncWorker(TERMS_CONDITIONS_SYNC_WORKER_ID, SyncGroup.TERMS_CONDITIONS)
}

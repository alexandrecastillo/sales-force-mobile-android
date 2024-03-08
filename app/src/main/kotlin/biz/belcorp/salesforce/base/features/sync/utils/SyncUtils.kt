package biz.belcorp.salesforce.base.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.base"
private const val HOME_SYNC_WORKER_ID = "$WORKER_PREFIX.HOME_SYNC_WORKER"
private const val HOME_ON_LOGIN_SYNC_WORKER_ID = "$WORKER_PREFIX.HOME_ON_LOGIN_SYNC_WORKER"
private const val HOME_ON_DEMAND_SYNC_WORKER_ID = "$WORKER_PREFIX.HOME_ON_DEMAND_SYNC_WORKER"

fun Context.startHomeOnLoginSyncWorker() {
    startSyncWorker(HOME_ON_LOGIN_SYNC_WORKER_ID, SyncGroup.HOME_ON_LOGIN)
}

fun Context.startHomeSyncWorker() {
    startSyncWorker(HOME_SYNC_WORKER_ID, SyncGroup.HOME)
}

fun Context.startHomeOnDemandSyncWorker() {
    startSyncWorker(HOME_ON_DEMAND_SYNC_WORKER_ID, SyncGroup.HOME_FORCED_ON_DEMAND)
}

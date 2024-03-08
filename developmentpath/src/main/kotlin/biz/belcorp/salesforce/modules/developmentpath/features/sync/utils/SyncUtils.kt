package biz.belcorp.salesforce.modules.developmentpath.features.sync.utils

import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.modules.developmentpath"
private const val DASHBOARD_SYNC_WORKER_ID = "$WORKER_PREFIX.DASHBOARD_SYNC_WORKER"

fun Fragment.startFullSyncWorker() {
    startSyncWorker(DASHBOARD_SYNC_WORKER_ID, SyncGroup.DEVELOPMENT_PATH_DASHBOARD)
}

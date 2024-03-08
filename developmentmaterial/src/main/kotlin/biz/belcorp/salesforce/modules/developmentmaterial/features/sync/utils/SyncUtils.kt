package biz.belcorp.salesforce.modules.developmentmaterial.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.developmentmaterial"
private const val MATERIAL_SYNC_WORKER_ID = "$WORKER_PREFIX.MATERIAL_SYNC_WORKER"

fun Context.startModuleSyncWorker() {
    startSyncWorker(MATERIAL_SYNC_WORKER_ID, SyncGroup.DEVELOPMENT_MATERIAL)
}

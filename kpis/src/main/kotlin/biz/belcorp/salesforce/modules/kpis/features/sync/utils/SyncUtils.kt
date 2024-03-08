package biz.belcorp.salesforce.modules.kpis.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.kpis"
private const val COLLECTION_EDA_SYNC_WORKER_ID = "$WORKER_PREFIX.COLLECTION_EDA_SYNC_WORKER"
private const val KPI_EDA_SYNC_WORKER_ID = "$WORKER_PREFIX.KPI_EDA_SYNC_WORKER"

fun Context.startCollectionSyncWorker() {
    startSyncWorker(COLLECTION_EDA_SYNC_WORKER_ID, SyncGroup.COLLECTION_EDA)
}

fun Context.startKpiSyncWorker() {
    startSyncWorker(KPI_EDA_SYNC_WORKER_ID, SyncGroup.KPI_EDA)
}

package biz.belcorp.salesforce.core.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.kpis"
private const val CAMPAIGNS_EDA_SYNC_WORKER_ID =
    "$WORKER_PREFIX.CAMPAIGNS_EDA_SYNC_WORKER"
private const val SEARCH_FILTERS_EDA_SYNC_WORKER_ID =
    "$WORKER_PREFIX.SEARCH_FILTERS_EDA_SYNC_WORKER"

fun Context.startCampaignsSyncWorker() {
    startSyncWorker(CAMPAIGNS_EDA_SYNC_WORKER_ID, SyncGroup.CAMPAIGNS_EDA)
}

fun Context.startSearchFiltersSyncWorker() {
    startSyncWorker(SEARCH_FILTERS_EDA_SYNC_WORKER_ID, SyncGroup.SEARCH_FILTERS_EDA)
}

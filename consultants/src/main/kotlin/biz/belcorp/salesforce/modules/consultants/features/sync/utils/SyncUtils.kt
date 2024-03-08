package biz.belcorp.salesforce.modules.consultants.features.sync.utils

import android.content.Context
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.utils.startSyncWorker

private const val WORKER_PREFIX = "biz.belcorp.salesforce.consultants"

private const val CONSULTANTS_EDA_SYNC_WORKER_ID =
    "$WORKER_PREFIX.CONSULTANTS_EDA_SYNC_WORKER"
private const val CONSULTANTS_LEGACY_SYNC_WORKER_ID =
    "$WORKER_PREFIX.CONSULTANTS_LEGACY_SYNC_WORKER"
private const val CONSULTANTS_PENDING_DEBT_SYNC_WORKER_ID =
    "$WORKER_PREFIX.CONSULTANTS_PENDING_DEBT_SYNC_WORKER"

fun Context.startConsultantsSyncWorker() {
    startSyncWorker(CONSULTANTS_EDA_SYNC_WORKER_ID, SyncGroup.CONSULTANTS_EDA)
}

fun Context.startConsultantsLegacySyncWorker() {
    startSyncWorker(CONSULTANTS_LEGACY_SYNC_WORKER_ID, SyncGroup.CONSULTANTS_LEGACY)
}

fun Context.startConsultantsPendingDebtSyncWorker() {
    startSyncWorker(CONSULTANTS_PENDING_DEBT_SYNC_WORKER_ID, SyncGroup.CONSULTANTS_PENDING_DEBT)
}

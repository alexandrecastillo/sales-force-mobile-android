package biz.belcorp.salesforce.core.sync

import android.util.Log
import biz.belcorp.salesforce.core.BuildConfig


abstract class SyncManager {

    protected var currentSyncGroup: SyncGroup? = null
    protected var isLoginSupport = false

    fun isAllowed(syncGroup: SyncGroup) = groups.contains(syncGroup)
        .also {
            if (it) printSyncGroup(syncGroup)
            currentSyncGroup = syncGroup
        }

    open val groups: List<SyncGroup> = mutableListOf()

    open suspend fun start() = Unit

    private fun printSyncGroup(syncGroup: SyncGroup) {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, syncGroup.name)
        }
    }

    fun isLoginSupport(isLoginSupport: Boolean) {
        isLoginSupport.also { this.isLoginSupport = it }
    }

}

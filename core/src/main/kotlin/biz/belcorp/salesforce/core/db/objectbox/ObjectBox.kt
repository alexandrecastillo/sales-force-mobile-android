package biz.belcorp.salesforce.core.db.objectbox

import android.content.Context
import android.util.Log
import biz.belcorp.salesforce.core.entities.MyObjectBox
import biz.belcorp.salesforce.core.utils.AppBuildConfig.isDebug
import biz.belcorp.salesforce.core.utils.BinaryUnitsUtils
import biz.belcorp.salesforce.core.utils.applyIf
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import io.objectbox.DebugFlags.LOG_ASYNC_QUEUE
import io.objectbox.DebugFlags.LOG_CACHE_ALL
import io.objectbox.DebugFlags.LOG_CACHE_HITS
import io.objectbox.DebugFlags.LOG_QUERIES
import io.objectbox.DebugFlags.LOG_QUERY_PARAMETERS
import io.objectbox.DebugFlags.LOG_TRANSACTIONS_READ
import io.objectbox.DebugFlags.LOG_TRANSACTIONS_WRITE
import io.objectbox.android.AndroidObjectBrowser
import java.io.File


object ObjectBox {

    private const val TAG = "App"

    lateinit var boxStore: BoxStore

    fun init(context: Context) {

        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .applyIf(isDebug()) { debugFlags(debugFlags) }
            .build()

        if (isDebug()) {
            Log.d(TAG, "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
            val started = AndroidObjectBrowser(boxStore).start(context.applicationContext)
            Log.i("ObjectBrowser", "Started: $started")
        }
    }

    fun sizeOnMB(): Double =
        BinaryUnitsUtils.bytesToMB(boxStore.sizeOnDisk().toDouble())

    private const val debugFlags =
        LOG_TRANSACTIONS_READ or LOG_TRANSACTIONS_WRITE or
            LOG_QUERIES or LOG_QUERY_PARAMETERS or
            LOG_ASYNC_QUEUE or LOG_CACHE_HITS or LOG_CACHE_ALL

}

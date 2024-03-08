package biz.belcorp.salesforce.core.db.dbflow

import android.content.Context
import biz.belcorp.salesforce.core.utils.AppBuildConfig.isDebug
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager


object DbFlow {

    fun init(context: Context, testing: Boolean) {
        FlowManager
            .init(
                FlowConfig.builder(context)
                    .openDatabasesOnInit(true)
                    .build()
            )
        setupDebugging(context, testing)
    }

    private fun setupDebugging(context: Context, testing: Boolean) {
        if (isDebug()) {
            FlowLog.setMinimumLoggingLevel(FlowLog.Level.V)
            if (!testing) {
                Stetho.initializeWithDefaults(context)
            }
        }
    }

}

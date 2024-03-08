package biz.belcorp.salesforce.base.utils.caoc

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import biz.belcorp.salesforce.base.utils.caoc.config.CaocConfig
import biz.belcorp.salesforce.base.utils.caoc.exceptions.NullContextException
import biz.belcorp.salesforce.base.utils.caoc.listeners.CaocActivityLifeCycle
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.CAOC_HANDLER_PACKAGE_NAME
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.DEFAULT_HANDLER_PACKAGE_NAME
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.EXTRA_CONFIG
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.EXTRA_STACK_TRACE
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.MAX_ACTIVITIES_IN_LOG
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.TAG
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.INSTALLED_ALREADY
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.INSTALL_ANOTHER_HANDLER_INSTALLED
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.INSTALL_CONTEXT_NULL_ERROR
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.INSTALL_DONE
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.INSTALL_UNKNOWN_ERROR
import biz.belcorp.salesforce.core.utils.OSUtils.killCurrentProcess
import biz.belcorp.salesforce.core.utils.isNotNull
import java.lang.Thread.UncaughtExceptionHandler
import java.lang.ref.WeakReference
import java.util.*

object Caoc {

    lateinit var application: Application
    var config = CaocConfig()
    val activityLog: Deque<String> = ArrayDeque(MAX_ACTIVITIES_IN_LOG)
    var lastActivityCreated = WeakReference<Activity?>(null)
    var lastActivityCreatedTimestamp = 0L
    var isInBackground = true

    fun install(context: Context?) {
        try {
            requireNotNull(context) { throw NullContextException() }
            with(Thread.getDefaultUncaughtExceptionHandler()) {
                if (isAlreadyInstalled(this)
                ) {
                    logE(INSTALLED_ALREADY)
                } else {
                    checkAnotherInstallation(this)
                    obtainApplication(context)
                    setHandler(this)
                    setActivityLifeCycles()
                }
            }
            Log.i(TAG, INSTALL_DONE)
        } catch (t: NullContextException) {
            logE(INSTALL_CONTEXT_NULL_ERROR)
        } catch (t: Throwable) {
            logE(INSTALL_UNKNOWN_ERROR, t)
        }
    }

    private fun isAlreadyInstalled(handler: UncaughtExceptionHandler?): Boolean {
        return handler.isNotNull() && handler?.javaClass?.name?.startsWith(CAOC_HANDLER_PACKAGE_NAME) ?: false
    }

    private fun checkAnotherInstallation(handler: UncaughtExceptionHandler?) {
        if (handler.isNotNull() && handler?.javaClass?.name?.startsWith(DEFAULT_HANDLER_PACKAGE_NAME) == false)
            logE(
                "$INSTALL_ANOTHER_HANDLER_INSTALLED (Handler: ${handler.javaClass.name} )"
            )
    }

    private fun obtainApplication(context: Context?) {
        application = context?.applicationContext as Application
    }

    private fun setHandler(oldHandler: UncaughtExceptionHandler?) {
        Thread.setDefaultUncaughtExceptionHandler(CaocHandler(oldHandler))
    }

    private fun setActivityLifeCycles() {
        application.registerActivityLifecycleCallbacks(CaocActivityLifeCycle())
    }

    fun restartApplication(activity: Activity, config: CaocConfig) {
        val intent = Intent(activity, config.restartActivityClass)
        restartApplicationWithIntent(activity, intent, config)
    }

    private fun restartApplicationWithIntent(
        activity: Activity,
        intent: Intent,
        config: CaocConfig
    ) {
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        )

        intent.component?.let {
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
        }
        config.caocEventListener?.onRestartAppFromErrorActivity()
        activity.finish()
        activity.startActivity(intent)
        killCurrentProcess()
    }

    private fun getStackTraceFromIntent(intent: Intent): String? {
        return intent.getStringExtra(EXTRA_STACK_TRACE)
    }

    fun getConfigFromIntent(intent: Intent): CaocConfig? {
        val config = intent.getSerializableExtra(EXTRA_CONFIG) as CaocConfig
        if (config.isNotNull() && config.logErrorOnRestart) {
            val stackTrace = getStackTraceFromIntent(intent)
            stackTrace?.let {
                Log.e(
                    TAG,
                    "${CaocMessages.PREVIOUS_APP_CRASHED}${getStackTraceFromIntent(intent)}".trimIndent()
                )
            }
        }
        return config
    }

    fun logE(message: String, t: Throwable? = null) =
        Log.e(TAG, message, t)

}

package biz.belcorp.salesforce.base.utils.caoc

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import biz.belcorp.salesforce.base.features.exception.UncaughtExceptionActivity
import biz.belcorp.salesforce.base.utils.caoc.Caoc.activityLog
import biz.belcorp.salesforce.base.utils.caoc.Caoc.application
import biz.belcorp.salesforce.base.utils.caoc.Caoc.config
import biz.belcorp.salesforce.base.utils.caoc.Caoc.isInBackground
import biz.belcorp.salesforce.base.utils.caoc.Caoc.lastActivityCreated
import biz.belcorp.salesforce.base.utils.caoc.Caoc.lastActivityCreatedTimestamp
import biz.belcorp.salesforce.base.utils.caoc.Caoc.logE
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_CRASH
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.ACTIVITY_THREAD_CLASS
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.ERROR_ACTIVIY_SUFFIX
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.EXTRA_ACTIVITY_LOG
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.EXTRA_CONFIG
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.EXTRA_EXCEPTION
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.EXTRA_STACK_TRACE
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.HANDLE_BIND_APPLICATION_METHOD
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.INTENT_ACTION_ERROR_ACTIVITY
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.INTENT_ACTION_RESTART_ACTIVITY
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.MAX_STACK_TRACE_SIZE
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.PROCCESS_CMD_LINE
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.SHARED_PREFERENCES_FIELD_TIMESTAMP
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.SHARED_PREFERENCES_FILE
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.TAG
import biz.belcorp.salesforce.base.utils.caoc.values.CaocConstants.TIME_TO_CONSIDER_FOREGROUND_MS
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.APPLICATION_OR_CUSTOM_CRASHED
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.FRAGMENT_EXCEPTION_PATTERN
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.STACK_TRACE_DISCLAIMER
import biz.belcorp.salesforce.core.domain.exceptions.UnavailableSessionException
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.OSUtils.killCurrentProcess
import biz.belcorp.salesforce.core.utils.applyIf
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.isNull
import java.io.*
import java.lang.Thread.UncaughtExceptionHandler
import java.lang.reflect.InvocationTargetException
import java.util.*

internal class CaocHandler(private val oldHandler: UncaughtExceptionHandler?) :
    UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        if (config.enabled) {
            logE(CaocMessages.EXECUTING_CAOC, throwable)
            if (hasCrashedInTheLastSeconds(application)) {
                logE(CaocMessages.CRASH_RECENTLY, throwable)
                oldHandler?.let {
                    it.uncaughtException(thread, throwable)
                    return
                }
            } else if (!applyConfiguration(oldHandler, thread, throwable)) {
                return
            }
            oldHandler?.uncaughtException(thread, throwable)
            finish()
        } else {
            oldHandler?.uncaughtException(thread, throwable)
        }
    }

    private fun applyConfiguration(
        oldHandler: UncaughtExceptionHandler?,
        thread: Thread,
        throwable: Throwable
    ): Boolean {
        setLastCrashTimestamp(application)
        when {
            isStackTraceLikelyConflictive(throwable) -> {
                logE(APPLICATION_OR_CUSTOM_CRASHED)
                oldHandler?.let {
                    it.uncaughtException(thread, throwable)
                    return false
                }
            }
            canShowHandler() -> {
                lastActivityCreated.get()?.applyIf(throwable.shouldBeIgnored()) {
                    notRecordExceptionConfig(throwable)
                    return false
                }
                exceptionConfig(throwable)
            }
            isBackgroundMode(oldHandler) -> {
                oldHandler?.uncaughtException(thread, throwable)
                return false
            }
        }
        return true
    }

    private fun exceptionConfig(throwable: Throwable) {
        val errorActivityClass = config.errorActivityClass ?: guessErrorActivityClass(application)
        val intent = Intent(application, errorActivityClass)
        buildStackTrace(throwable, intent)
        configTrackActivities(intent)
        if (config.showRestartButton && config.restartActivityClass.isNull()) {
            config.restartActivityClass = guessRestartActivityClass(application)
        }
        intent.putExtra(EXTRA_CONFIG, config)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        config.caocEventListener?.onLaunchErrorActivity()
        application.startActivity(intent)

        Logger.e(throwable)
    }

    private fun canShowHandler(): Boolean {
        return config.backgroundMode == BACKGROUND_MODE_SHOW_CUSTOM || !isInBackground
            || lastActivityCreatedTimestamp >= Date().time - TIME_TO_CONSIDER_FOREGROUND_MS
    }

    private fun notRecordExceptionConfig(throwable: Throwable) {
        val intent = Intent(application, config.restartActivityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.putExtra(EXTRA_EXCEPTION, throwable)
        lastActivityCreated.get()?.finish()
        lastActivityCreated.get()?.startActivity(intent)
        lastActivityCreated.clear()
        killCurrentProcess()
    }

    private fun isBackgroundMode(oldHandler: UncaughtExceptionHandler?) =
        config.backgroundMode == BACKGROUND_MODE_CRASH && oldHandler.isNotNull()

    private fun buildStackTrace(throwable: Throwable, intent: Intent) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable.printStackTrace(pw)
        var stackTraceString = sw.toString()
        if (stackTraceString.length > MAX_STACK_TRACE_SIZE) {
            stackTraceString =
                stackTraceString.substring(
                    0,
                    MAX_STACK_TRACE_SIZE - STACK_TRACE_DISCLAIMER.length
                ) + STACK_TRACE_DISCLAIMER
        }
        intent.putExtra(EXTRA_STACK_TRACE, stackTraceString)
    }

    private fun configTrackActivities(intent: Intent) {
        if (config.trackActivities) {
            val activityLogStringBuilder = StringBuilder()
            while (activityLog.isNotEmpty())
                activityLogStringBuilder.append(activityLog.poll())
            intent.putExtra(EXTRA_ACTIVITY_LOG, activityLogStringBuilder.toString())
        }
    }

    private fun finish() {
        lastActivityCreated.get()?.let {
            it.finish()
            lastActivityCreated.clear()
        }
        killCurrentProcess()
    }

    private fun Throwable.shouldBeIgnored(): Boolean {
        return isIgnoredException() || isIgnoredMessage()
    }

    private fun Throwable.isIgnoredException(): Boolean {
        return if (this.isIgnored() || cause.isIgnored())
            true
        else {
            try {
                (cause as InvocationTargetException).targetException.isIgnored()
            } catch (ex: Exception) {
                false
            }
        }
    }

    private fun Throwable?.isIgnored(): Boolean = this is UnavailableSessionException

    private fun Throwable.isIgnoredMessage(): Boolean {
        message?.let {
            return it.contains(FRAGMENT_EXCEPTION_PATTERN)
        }
        return false
    }

    private fun getLastCrashTimestamp(context: Context): Long {
        return context.getSharedPreferences(
            SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE
        ).getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, -1)
    }

    private fun hasCrashedInTheLastSeconds(context: Context): Boolean {
        val lastTimestamp = getLastCrashTimestamp(context)
        val currentTimestamp = Date().time
        return lastTimestamp <= currentTimestamp &&
            currentTimestamp - lastTimestamp < config.minTimeBetweenCrashesMs
    }

    @SuppressLint("ApplySharedPref")
    private fun setLastCrashTimestamp(context: Context) {
        context.getSharedPreferences(
            SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE
        ).edit().putLong(
            SHARED_PREFERENCES_FIELD_TIMESTAMP, Date().time
        ).commit()
    }

    private fun guessErrorActivityClass(context: Context): Class<out Activity?> {
        return getErrorActivityClassWithIntentFilter(context)
            ?: UncaughtExceptionActivity::class.java
    }

    private fun getErrorActivityClassWithIntentFilter(context: Context): Class<out Activity?>? {
        val searchedIntent = Intent().setAction(INTENT_ACTION_ERROR_ACTIVITY)
            .setPackage(context.packageName)
        val resolveInfos = context.packageManager.queryIntentActivities(
            searchedIntent,
            PackageManager.GET_RESOLVED_FILTER
        )
        if (resolveInfos.isNotEmpty()) {
            val resolveInfo = resolveInfos[0]
            try {
                return Class.forName(resolveInfo.activityInfo.name) as Class<out Activity?>
            } catch (e: ClassNotFoundException) {
                Log.e(TAG, CaocMessages.RESOLVING_ERRROR_ACTIVITY_FAILED, e)
            }
        }
        return null
    }

    private fun guessRestartActivityClass(context: Context): Class<out Activity?>? {
        return getRestartActivityClassWithIntentFilter(context) ?: getLauncherActivity(
            context
        )
    }

    private fun getRestartActivityClassWithIntentFilter(context: Context): Class<out Activity?>? {
        val searchedIntent =
            Intent().setAction(INTENT_ACTION_RESTART_ACTIVITY)
                .setPackage(context.packageName)
        val resolveInfos = context.packageManager.queryIntentActivities(
            searchedIntent,
            PackageManager.GET_RESOLVED_FILTER
        )
        if (resolveInfos.isNotEmpty()) {
            val resolveInfo = resolveInfos[0]
            try {
                return Class.forName(resolveInfo.activityInfo.name) as Class<out Activity?>
            } catch (e: ClassNotFoundException) {
                Log.e(
                    TAG, CaocMessages.RESOLVING_RESTART_ACTIVITY_INTENT_FAILED, e
                )
            }
        }
        return null
    }

    private fun getLauncherActivity(context: Context): Class<out Activity?>? {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.component?.let {
            try {
                return Class.forName(it.className) as Class<out Activity?>
            } catch (e: ClassNotFoundException) {
                Log.e(TAG, CaocMessages.RESOLVING_RESTART_ACTIVITY_FAILED)
            }
        }
        return null
    }

    private fun isStackTraceLikelyConflictive(
        throwable: Throwable
    ): Boolean {
        var t = throwable
        var process: String? = null
        try {
            BufferedReader(FileReader(PROCCESS_CMD_LINE))
                .use { br -> process = br.readLine().trim() }
        } catch (e: IOException) {
            process = null
        }
        if (process?.endsWith(ERROR_ACTIVIY_SUFFIX) == true) {
            return true
        }
        do {
            t.stackTrace.forEach { e ->
                if (e.className == ACTIVITY_THREAD_CLASS &&
                    e.methodName == HANDLE_BIND_APPLICATION_METHOD
                ) return true
            }
            t.cause?.let { t = it }
        } while (t.cause.isNotNull())
        return false
    }

}

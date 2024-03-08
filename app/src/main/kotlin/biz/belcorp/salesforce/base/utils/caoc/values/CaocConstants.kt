package biz.belcorp.salesforce.base.utils.caoc.values


object CaocConstants {

    const val TAG = "CustomActivityOnCrash"

    const val EXTRA_CONFIG = "biz.belcorp.salesforce.EXTRA_CONFIG"
    const val EXTRA_EXCEPTION = "biz.belcorp.salesforce.EXTRA_EXCEPTION"
    const val EXTRA_STACK_TRACE = "biz.belcorp.salesforce.EXTRA_STACK_TRACE"
    const val EXTRA_ACTIVITY_LOG = "biz.belcorp.salesforce.EXTRA_ACTIVITY_LOG"

    const val INTENT_ACTION_ERROR_ACTIVITY = "biz.belcorp.salesforce.ERROR"
    const val INTENT_ACTION_RESTART_ACTIVITY = "biz.belcorp.salesforce.RESTART"
    const val CAOC_HANDLER_PACKAGE_NAME = "biz.belcorp.salesforce"
    const val DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os"
    const val TIME_TO_CONSIDER_FOREGROUND_MS = 500
    const val MAX_STACK_TRACE_SIZE = 131071 //128 KB - 1
    const val MAX_ACTIVITIES_IN_LOG = 50

    const val SHARED_PREFERENCES_FILE = "custom_activity_on_crash"
    const val SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_crash_timestamp"

    const val PROCCESS_CMD_LINE = "/proc/self/cmdline"
    const val ERROR_ACTIVIY_SUFFIX = ":error_activity"
    const val ACTIVITY_THREAD_CLASS = "android.app.ActivityThread"
    const val HANDLE_BIND_APPLICATION_METHOD = "handleBindApplication"

}

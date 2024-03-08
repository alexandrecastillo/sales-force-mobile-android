package biz.belcorp.salesforce.base.utils.caoc.listeners

import android.app.Activity
import android.app.Application
import android.os.Bundle
import biz.belcorp.salesforce.base.utils.caoc.Caoc.activityLog
import biz.belcorp.salesforce.base.utils.caoc.Caoc.config
import biz.belcorp.salesforce.base.utils.caoc.Caoc.isInBackground
import biz.belcorp.salesforce.base.utils.caoc.Caoc.lastActivityCreated
import biz.belcorp.salesforce.base.utils.caoc.Caoc.lastActivityCreatedTimestamp
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CaocActivityLifeCycle : Application.ActivityLifecycleCallbacks {

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
        private const val CREATED = "created"
        private const val RESUMED = "resumed"
        private const val PAUSED = "paused"
        private const val DESTROYED = "destroyed"
    }

    private var startedActivities = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity.javaClass != config.errorActivityClass) {
            lastActivityCreated = WeakReference(activity)
            lastActivityCreatedTimestamp = Date().time
        }
        activity.track(CREATED)
    }

    override fun onActivityStarted(activity: Activity) {
        startedActivities++
        checkIsInBackground()
    }

    override fun onActivityResumed(activity: Activity) {
        activity.track(RESUMED)
    }

    override fun onActivityPaused(activity: Activity) {
        activity.track(PAUSED)
    }

    override fun onActivityStopped(activity: Activity) {
        startedActivities--
        checkIsInBackground()
    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
    ) {/*we must override behaviour*/
    }

    override fun onActivityDestroyed(activity: Activity) {
        activity.track(DESTROYED)
    }

    private fun checkIsInBackground() {
        isInBackground = (startedActivities == 0)
    }

    private fun Activity.track(callback: String) {
        val dateFormat: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.US)
        if (config.trackActivities) {
            val log = "${dateFormat.format(Date())}: ${this.javaClass.simpleName} $callback"
            activityLog.add(log)
        }
    }

}

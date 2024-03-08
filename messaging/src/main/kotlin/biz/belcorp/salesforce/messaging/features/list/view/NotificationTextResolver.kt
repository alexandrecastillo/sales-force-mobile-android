package biz.belcorp.salesforce.messaging.features.list.view

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.ElapsedTime
import biz.belcorp.salesforce.core.utils.Time.Hours
import biz.belcorp.salesforce.core.utils.Time.Minutes
import biz.belcorp.salesforce.core.utils.Time.Seconds
import biz.belcorp.salesforce.core.utils.Time.Days
import biz.belcorp.salesforce.core.utils.Time.Weeks
import biz.belcorp.salesforce.core.utils.Time.Specific
import biz.belcorp.salesforce.messaging.R

class NotificationTextResolver(private val context: Context) : BaseTextResolver() {

    fun formatElapsedTime(elapsedTime: ElapsedTime): String = with(elapsedTime) {
        return when (unitTime) {
            is Seconds -> formatSeconds()
            is Minutes -> formatMinutes(count)
            is Hours -> formatHours(count)
            is Days -> formatDays(count)
            is Weeks -> formatWeeks(count)
            is Specific -> description
            else -> Constant.EMPTY_STRING
        }
    }

    private fun formatSeconds(): String {
        return context.getString(R.string.notification_elapsed_time_seconds)
    }

    private fun formatMinutes(minutes: Long): String {
        return context.resources.getQuantityString(
            R.plurals.plural_notification_elapsed_time_minutes,
            minutes.toInt(),
            minutes.toInt()
        )
    }

    private fun formatHours(hours: Long): String {
        return context.resources.getQuantityString(
            R.plurals.plural_notification_elapsed_time_hours,
            hours.toInt(),
            hours.toInt()
        )
    }

    private fun formatDays(days: Long): String {
        return context.resources.getQuantityString(
            R.plurals.plural_notification_elapsed_time_days,
            days.toInt(),
            days.toInt()
        )
    }

    private fun formatWeeks(weeks: Long): String {
        return context.getString(R.string.notification_elapsed_time_weeks, weeks.toInt())
    }
}

package biz.belcorp.salesforce.core.sync.helpers

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.R
import biz.belcorp.salesforce.core.utils.setHighPriorityChannel
import biz.belcorp.salesforce.core.utils.setIndeterminateProgress


class SyncNotificationHelper(private val appContext: Context) {

    companion object {

        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "CHANNEL_NAME"

        private const val NOTIFICATION_ID = 1000

    }

    private val notificationManager: NotificationManager by lazy {
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val title by lazy { appContext.getString(R.string.notification_title_sync) }
    private val content by lazy { appContext.getString(R.string.notification_content_sync) }
    private val color by lazy { ContextCompat.getColor(appContext, R.color.magenta) }

    fun showNotification() {
        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setColor(color)
            .setIndeterminateProgress()
            .setHighPriorityChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                notificationManager
            )
            .setContentText(content)
            .setOngoing(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun hideNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

}

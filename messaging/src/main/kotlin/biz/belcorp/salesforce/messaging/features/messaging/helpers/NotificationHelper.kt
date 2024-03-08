package biz.belcorp.salesforce.messaging.features.messaging.helpers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.utils.setHighPriorityChannel
import biz.belcorp.salesforce.messaging.R
import biz.belcorp.salesforce.messaging.core.domain.entities.Notification


abstract class NotificationHelper(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val appBuild: AppBuild
) {

    protected abstract val channelId: String
    protected abstract val channelName: String
    protected abstract val groupKeySuffix: String

    fun manage(notification: Notification, uri: Uri) {

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            Constant.NUMBER_ZERO,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationBuilder = NotificationCompat.Builder(context, Constant.EMPTY_STRING)
            .setHighPriorityChannel(channelId, channelName, notificationManager)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .setColor(ContextCompat.getColor(context, R.color.notification_color))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setDefaults(android.app.Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setGroup(groupKeyBuilt())

        notificationManager.notify(notification.id.toInt(), notificationBuilder.build())
    }

    private fun groupKeyBuilt(): String {
        return appBuild.applicationId.plus(groupKeySuffix)
    }

}

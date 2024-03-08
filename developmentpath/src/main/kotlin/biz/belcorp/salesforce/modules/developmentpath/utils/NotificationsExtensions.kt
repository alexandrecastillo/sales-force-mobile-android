package biz.belcorp.salesforce.modules.developmentpath.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

@SuppressLint("NewApi")
fun NotificationCompat.Builder.setChannel(
    channelId: String,
    channelName: String,
    notificationManager: NotificationManager
): NotificationCompat.Builder {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableLights(true)
        notificationManager.createNotificationChannel(notificationChannel)
        setChannelId(channelId)
    }

    return this
}

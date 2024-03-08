package biz.belcorp.salesforce.messaging.features.messaging.helpers

import android.app.NotificationManager
import android.content.Context
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.utils.AppUri
import biz.belcorp.salesforce.core.utils.AppUri.ACTION_KEY
import biz.belcorp.salesforce.core.utils.AppUri.CODE_KEY
import biz.belcorp.salesforce.core.utils.withParameters
import biz.belcorp.salesforce.messaging.core.domain.entities.PostulantsNotification


class PostulantsNotificationHelper(
    context: Context,
    notificationManager: NotificationManager,
    appBuild: AppBuild
) : NotificationHelper(context, notificationManager, appBuild) {

    companion object {

        const val CHANNEL_ID = "2000"
        const val CHANNEL_NAME = "HIGH_PRIORITY"

        const val GROUP_KEY_SUFFIX = ".POSTULANTS"

        const val POSTULANTS_ACTION_VALUE = "UNETE"

    }

    override val channelId = CHANNEL_ID
    override val channelName = CHANNEL_NAME

    override val groupKeySuffix: String = GROUP_KEY_SUFFIX

    fun manage(notification: PostulantsNotification) {

        val uri = AppUri.create()
            .withParameters(
                ACTION_KEY to POSTULANTS_ACTION_VALUE,
                CODE_KEY to notification.id.toString()
            )
            .build()

        manage(notification, uri)
    }

}

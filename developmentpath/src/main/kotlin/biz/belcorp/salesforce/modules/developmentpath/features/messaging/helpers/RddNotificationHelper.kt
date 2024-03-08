package biz.belcorp.salesforce.modules.developmentpath.features.messaging.helpers

import android.app.NotificationManager
import android.content.Context
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.utils.AppUri
import biz.belcorp.salesforce.core.utils.AppUri.ACTION_KEY
import biz.belcorp.salesforce.core.utils.AppUri.CODE_KEY
import biz.belcorp.salesforce.core.utils.withParameters
import biz.belcorp.salesforce.messaging.core.domain.entities.RddRecognitionNotification
import biz.belcorp.salesforce.messaging.features.messaging.helpers.NotificationHelper


class RddNotificationHelper(
    context: Context,
    notificationManager: NotificationManager,
    appBuild: AppBuild
) : NotificationHelper(context, notificationManager, appBuild) {

    companion object {

        const val CHANNEL_ID = "3000"
        const val CHANNEL_NAME = "HIGH_PRIORITY"

        const val RECOGNITION_GROUP_KEY_SUFFIX = ".RECOGNITION"

        const val RECOGNITION_ACTION_VALUE = "RDD"

    }

    override val channelId = CHANNEL_ID
    override val channelName = CHANNEL_NAME

    override val groupKeySuffix: String = RECOGNITION_GROUP_KEY_SUFFIX

    fun manage(notification: RddRecognitionNotification) {

        val code = notification.data?.id ?: return

        val uri = AppUri.create()
            .withParameters(
                ACTION_KEY to RECOGNITION_ACTION_VALUE,
                CODE_KEY to code.toString()
            )
            .build()

        manage(notification, uri)
    }

}

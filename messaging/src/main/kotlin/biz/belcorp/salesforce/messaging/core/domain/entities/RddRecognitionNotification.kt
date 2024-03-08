package biz.belcorp.salesforce.messaging.core.domain.entities

import biz.belcorp.salesforce.core.utils.notification.RddRecognitionData
import biz.belcorp.salesforce.core.utils.safeJsonParse


class RddRecognitionNotification : Notification() {

    val data by lazy { safeJsonParse(RddRecognitionData.serializer(), dataString) }

}

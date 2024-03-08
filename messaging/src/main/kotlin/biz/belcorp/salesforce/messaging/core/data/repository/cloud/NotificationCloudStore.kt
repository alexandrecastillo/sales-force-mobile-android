package biz.belcorp.salesforce.messaging.core.data.repository.cloud

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.notification.NotificationDataParser
import biz.belcorp.salesforce.core.utils.safeApiCall
import biz.belcorp.salesforce.messaging.core.data.network.MessagingApi
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.*

class NotificationCloudStore(
    private val messagingApi: MessagingApi
) {

    suspend fun updateNotificationSeen(query: NotificationSeenQuery) {
        val response = safeApiCall {
            messagingApi.updateNotificationSeen(query)
        }
        requireNotNull(response?.resultado)
    }

    suspend fun getNotificationList(query: NotificationListQuery): List<NotificationListResponse.Notification> {
        val response = safeApiCall {
            messagingApi.getNotifications(query)
        }

        return requireNotNull(response).cleanData().resultado
    }

    suspend fun getToken(query: SecurityQuery): SecurityResponse {
        val response = safeApiCall {
            messagingApi.getToken(query)
        }
        return requireNotNull(response)
    }

    private fun NotificationListResponse.cleanData() = apply {
        resultado.map {
            val cleanData = NotificationDataParser.parse(it.data)
            it.subtopic = cleanData?.topic.orEmpty()
            it.data = cleanData?.data ?: Constant.EMPTY_OBJECT
        }
    }

}

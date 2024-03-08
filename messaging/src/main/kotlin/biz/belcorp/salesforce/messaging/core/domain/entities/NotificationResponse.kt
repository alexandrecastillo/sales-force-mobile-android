package biz.belcorp.salesforce.messaging.core.domain.entities

data class NotificationResponse(
    val todayNotifications: List<Notification> = listOf(),
    val otherNotifications: List<Notification> = listOf()
) {
    var hasPendingNewsNotifcations = false
    var hasPendingRddNotifcations = false
    var hasPendingPostulantsNotifications = false

    val hasTodayNotifications get() = todayNotifications.isNotEmpty()
    val hasOtherNotifications get() = otherNotifications.isNotEmpty()
}

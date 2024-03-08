package biz.belcorp.salesforce.messaging.features.list.model

data class NotificationContainer(
    val todayNotifications: List<NotificationModel> = listOf(),
    val otherNotifications: List<NotificationModel> = listOf()
) {
    var hasPendingNewsNotifications = false
    var hasPendingRddNotifications = false
    var hasPendingPostulantsNotifications = false

    val hasTodayNotifications get() = todayNotifications.isNotEmpty()
    val hasOtherNotifications get() = otherNotifications.isNotEmpty()
}

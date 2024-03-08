package biz.belcorp.salesforce.messaging.features.list.view

sealed class NotificationPendingViewState :
    BaseNotificationViewState {
    class Success(val model: Boolean) : NotificationPendingViewState()
    class Failure(val message: String) : NotificationPendingViewState()
}

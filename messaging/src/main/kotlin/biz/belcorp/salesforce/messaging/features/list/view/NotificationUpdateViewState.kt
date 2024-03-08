package biz.belcorp.salesforce.messaging.features.list.view

import biz.belcorp.salesforce.messaging.features.list.model.NotificationModel

sealed class NotificationUpdateViewState :
    BaseNotificationViewState {
    class Success(val model: NotificationModel) : NotificationUpdateViewState()
    class Failure(val message: String) : NotificationUpdateViewState()
}

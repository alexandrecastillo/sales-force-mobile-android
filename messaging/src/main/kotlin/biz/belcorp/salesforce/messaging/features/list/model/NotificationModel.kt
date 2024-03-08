package biz.belcorp.salesforce.messaging.features.list.model

import androidx.annotation.ColorRes
import androidx.annotation.IdRes

data class NotificationModel(
    val code: Long,
    val message: String,
    val topic: String,
    @IdRes val icon: Int,
    @ColorRes val iconColor: Int,
    @ColorRes val backgroundColor: Int,
    val elapsedTime: String,
    var seen: Boolean,
    val type: Int,
    var uriParams: HashMap<String, String?> = hashMapOf()
)

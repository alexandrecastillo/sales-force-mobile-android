package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.PREFIX_TOPIC
import com.google.firebase.messaging.RemoteMessage


val RemoteMessage.topic get() = from?.replace(PREFIX_TOPIC, EMPTY_STRING)

val RemoteMessage.jsonData get() = data.parseToJson()

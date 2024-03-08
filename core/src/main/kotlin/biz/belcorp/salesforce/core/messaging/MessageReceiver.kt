package biz.belcorp.salesforce.core.messaging

import biz.belcorp.salesforce.core.messaging.model.Payload


interface MessageReceiver {

    val subtopics: List<String>

    fun onReceiveMessage(payload: Payload)

}

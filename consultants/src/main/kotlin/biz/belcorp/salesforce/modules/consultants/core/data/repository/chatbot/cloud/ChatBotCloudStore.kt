package biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.cloud

import biz.belcorp.salesforce.core.utils.safeApiCall
import biz.belcorp.salesforce.modules.consultants.core.data.network.ChatBotApi

class ChatBotCloudStore(private val chatBotApi: ChatBotApi) {
    suspend fun getChatBotUrl(): String {
        val response = safeApiCall { chatBotApi.getChatBotUrl() }
        return requireNotNull(response)
    }
}

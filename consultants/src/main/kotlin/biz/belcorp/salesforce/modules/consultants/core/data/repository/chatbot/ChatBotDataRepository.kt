package biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot

import biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.cloud.ChatBotCloudStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.chatbot.ChatBotRepository

class ChatBotDataRepository(private val chatBotCloudStore: ChatBotCloudStore) : ChatBotRepository {
    override suspend fun getChatBotUrl(): String {
        return chatBotCloudStore.getChatBotUrl()
    }
}

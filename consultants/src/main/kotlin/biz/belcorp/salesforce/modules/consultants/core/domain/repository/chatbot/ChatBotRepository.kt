package biz.belcorp.salesforce.modules.consultants.core.domain.repository.chatbot

interface ChatBotRepository {
    suspend fun getChatBotUrl(): String
}

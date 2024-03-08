package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot

import biz.belcorp.salesforce.modules.consultants.core.domain.repository.chatbot.ChatBotRepository

class GetChatBotUrlUseCase(
    private val chatBotRepository: ChatBotRepository
) {

    suspend fun getChatBotUrl(): String {
        return chatBotRepository.getChatBotUrl()
    }
}

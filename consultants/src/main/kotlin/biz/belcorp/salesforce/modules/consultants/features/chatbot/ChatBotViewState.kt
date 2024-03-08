package biz.belcorp.salesforce.modules.consultants.features.chatbot

sealed class ChatBotViewState {
    class ChatBotSuccess(val url: String) : ChatBotViewState()
    class ChatBotError(val message : String) : ChatBotViewState()

}

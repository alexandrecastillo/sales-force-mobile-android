package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot.GetChatBotUrlUseCase
import org.koin.dsl.module

val chatBotModule = module {
    factory { GetChatBotUrlUseCase(get()) }
}

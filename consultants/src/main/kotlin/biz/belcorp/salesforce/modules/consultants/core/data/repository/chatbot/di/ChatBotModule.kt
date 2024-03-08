package biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.ChatBotDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.cloud.ChatBotCloudStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.chatbot.ChatBotRepository
import org.koin.dsl.module

val chatBotModule = module {

    factory { ChatBotCloudStore(get()) }

    factory<ChatBotRepository> { ChatBotDataRepository(get()) }

}

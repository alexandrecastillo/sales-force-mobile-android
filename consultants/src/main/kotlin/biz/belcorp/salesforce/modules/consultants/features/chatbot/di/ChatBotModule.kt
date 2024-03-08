package biz.belcorp.salesforce.modules.consultants.features.chatbot.di

import biz.belcorp.salesforce.modules.consultants.features.chatbot.ChatBotTextResolver
import biz.belcorp.salesforce.modules.consultants.features.chatbot.ChatBotViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatBotModule = module {
    factory { ChatBotTextResolver(get()) }
    viewModel { ChatBotViewModel(get(), get(), get()) }
}

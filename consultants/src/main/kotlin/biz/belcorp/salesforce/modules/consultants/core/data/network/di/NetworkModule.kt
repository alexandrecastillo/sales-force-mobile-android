package biz.belcorp.salesforce.modules.consultants.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.core.data.network.utils.createOkHttpClient
import biz.belcorp.salesforce.core.data.network.utils.createRetrofitNew
import biz.belcorp.salesforce.modules.consultants.BuildConfig
import biz.belcorp.salesforce.modules.consultants.core.data.network.ChatBotApi
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultantsApi
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultorasApi
import biz.belcorp.salesforce.modules.consultants.core.data.network.GeoLocationApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val NAMED_CHATBOT = "CHATBOT"
const val NAMED_CHATBOT_CLIENT = "CHATBOT_CLIENT"

val networkModule = module {
    single(named(NAMED_CHATBOT_CLIENT)) { createOkHttpClient(get(), get()) }
    single(named(NAMED_CHATBOT)) { retrofitChatBot(get(named(NAMED_CHATBOT_CLIENT))) }

    single { provideConsultorasApi(get(named(NAMED_MOBILE))) }
    single { provideGeoLocationApi(get(named(NAMED_MOBILE))) }
    single { provideConsultantsApi(get(named(NAMED_BELCORP))) }
    single { provideChatBotApi(get(named(NAMED_CHATBOT))) }
}


private fun provideGeoLocationApi(retrofit: Retrofit): GeoLocationApi =
    retrofit.create(GeoLocationApi::class.java)

private fun provideConsultorasApi(retrofit: Retrofit): ConsultorasApi {
    return retrofit.create(ConsultorasApi::class.java)
}

private fun provideConsultantsApi(retrofit: Retrofit): ConsultantsApi {
    return retrofit.create(ConsultantsApi::class.java)
}

private fun retrofitChatBot(okHttpClient: OkHttpClient): Retrofit {
    return createRetrofitNew(okHttpClient, BuildConfig.CHATBOT_HOST)
}

private fun provideChatBotApi(retrofit: Retrofit): ChatBotApi {
    return retrofit.create(ChatBotApi::class.java)
}

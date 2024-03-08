package biz.belcorp.salesforce.messaging.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_SOMOS_BELCORP_NEW
import biz.belcorp.salesforce.messaging.core.data.network.MessagingApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


internal val networkModule = module {
    single { provideMessagingApi(get(named(NAMED_SOMOS_BELCORP_NEW))) }
}

private fun provideMessagingApi(retrofit: Retrofit): MessagingApi {
    return retrofit.create(MessagingApi::class.java)
}

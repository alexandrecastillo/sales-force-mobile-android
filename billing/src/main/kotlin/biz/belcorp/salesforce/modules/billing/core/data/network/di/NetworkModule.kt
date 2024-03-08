package biz.belcorp.salesforce.modules.billing.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.modules.billing.core.data.network.RejectedOrdersApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideRejectedOrdersApi(get(named(NAMED_BELCORP))) }
}

private fun provideRejectedOrdersApi(retrofit: Retrofit): RejectedOrdersApi {
    return retrofit.create(RejectedOrdersApi::class.java)
}

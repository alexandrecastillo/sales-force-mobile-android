package biz.belcorp.salesforce.modules.inspires.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.inspires.core.data.network.InspiresApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val networkModule = module {
    single {
        provideInspiresApi(
            get(named(NAMED_MOBILE))
        )
    }
}

private fun provideInspiresApi(retrofit: Retrofit): InspiresApi {
    return retrofit.create(InspiresApi::class.java)
}

package biz.belcorp.salesforce.base.core.data.network.di


import biz.belcorp.salesforce.base.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single { provideBelcorApi(get(named(NAMED_BELCORP))) }
}

private fun provideBelcorApi(retrofit: Retrofit): BelcorpApi {
    return retrofit.create(BelcorpApi::class.java)
}

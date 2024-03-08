package biz.belcorp.salesforce.modules.auth.core.data.network.di


import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE_NEW
import biz.belcorp.salesforce.modules.auth.core.data.network.AuthApi
import biz.belcorp.salesforce.modules.auth.core.data.network.LegacyAuthApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single { provideLoginApi(get(named(NAMED_BELCORP))) }
    single { provideLegacyLoginApi(get(named(NAMED_MOBILE_NEW))) }

}

private fun provideLoginApi(retrofit: Retrofit): AuthApi {
    return retrofit.create(AuthApi::class.java)
}

private fun provideLegacyLoginApi(retrofit: Retrofit): LegacyAuthApi {
    return retrofit.create(LegacyAuthApi::class.java)
}

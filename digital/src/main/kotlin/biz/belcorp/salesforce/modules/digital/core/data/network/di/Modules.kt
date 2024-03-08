package biz.belcorp.salesforce.modules.digital.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.modules.digital.core.data.network.DigitalApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val networkModule = module {

    single { provideBelcorpApi(get(named(NAMED_BELCORP))) }

}

private fun provideBelcorpApi(retrofit: Retrofit): DigitalApi {
    return retrofit.create(DigitalApi::class.java)
}

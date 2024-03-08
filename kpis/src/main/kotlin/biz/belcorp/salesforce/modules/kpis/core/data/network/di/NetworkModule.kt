package biz.belcorp.salesforce.modules.kpis.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


internal val networkModule = module {

    single { provideBelcorpApi(get(named(NAMED_BELCORP))) }

}

private fun provideBelcorpApi(retrofit: Retrofit): BelcorpApi {
    return retrofit.create(BelcorpApi::class.java)
}

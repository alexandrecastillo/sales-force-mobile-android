package biz.belcorp.salesforce.modules.brightpath.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.brightpath.core.data.network.BrightPathApi
import biz.belcorp.salesforce.modules.brightpath.core.data.network.BrightPathGraphApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {

    single { provideBrightPathApi(get(named(NAMED_MOBILE))) }

    single { provideSyncBusinessPartnerChangeLevelApi(get((named(NAMED_BELCORP)))) }

}

private fun provideBrightPathApi(retrofit: Retrofit): BrightPathApi =
    retrofit.create(BrightPathApi::class.java)

private fun provideSyncBusinessPartnerChangeLevelApi(retrofit: Retrofit): BrightPathGraphApi {
    return retrofit.create(BrightPathGraphApi::class.java)
}


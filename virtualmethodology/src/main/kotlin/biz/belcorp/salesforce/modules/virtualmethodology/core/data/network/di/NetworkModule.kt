package biz.belcorp.salesforce.modules.virtualmethodology.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.network.VirtualMethodologyApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {

    single { provideVirtualMethodologyApi(get(named(NAMED_MOBILE))) }

}

private fun provideVirtualMethodologyApi(retrofit: Retrofit): VirtualMethodologyApi {
    return retrofit.create(VirtualMethodologyApi::class.java)
}

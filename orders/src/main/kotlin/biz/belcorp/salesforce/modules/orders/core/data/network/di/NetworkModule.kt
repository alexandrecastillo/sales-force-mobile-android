package biz.belcorp.salesforce.modules.orders.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.orders.core.data.network.PedidosWebApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {

    single { provideOrdersApi(get(named(NAMED_MOBILE))) }

}

private fun provideOrdersApi(retrofit: Retrofit): PedidosWebApi {
    return retrofit.create(PedidosWebApi::class.java)
}

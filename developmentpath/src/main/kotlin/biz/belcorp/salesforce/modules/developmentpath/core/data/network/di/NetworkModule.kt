package biz.belcorp.salesforce.modules.developmentpath.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single { provideMetaConsultoraApi(get(named(NAMED_MOBILE))) }
    single { provideGraficosApi(get(named(NAMED_MOBILE))) }
    single { provideGoogleApi(get(named(NAMED_MOBILE))) }
    single { provideTipsOfertasApi(get(named(NAMED_MOBILE))) }
    single { provideSyncRestApi(get(named(NAMED_MOBILE))) }
    single { provideGeorreferenceApi(get(named(NAMED_MOBILE))) }
    single { provideSyncProfileInfoApi(get((named(NAMED_BELCORP)))) }

}

private fun provideGoogleApi(retrofit: Retrofit): GoogleApi {
    return retrofit.create(GoogleApi::class.java)
}

private fun provideGraficosApi(retrofit: Retrofit): GraficosApi {
    return retrofit.create(GraficosApi::class.java)
}

private fun provideSyncRestApi(retrofit: Retrofit): SyncRestApi {
    return retrofit.create(SyncRestApi::class.java)
}

private fun provideMetaConsultoraApi(retrofit: Retrofit): PerfilApi {
    return retrofit.create(PerfilApi::class.java)
}

private fun provideTipsOfertasApi(retrofit: Retrofit): TipsOfertasRestApi {
    return retrofit.create(TipsOfertasRestApi::class.java)
}

private fun provideGeorreferenceApi(retrofit: Retrofit): GeorreferenciaApi {
    return retrofit.create(GeorreferenciaApi::class.java)
}

private fun provideSyncProfileInfoApi(retrofit: Retrofit): SyncProfileInfoApi {
    return retrofit.create(SyncProfileInfoApi::class.java)
}

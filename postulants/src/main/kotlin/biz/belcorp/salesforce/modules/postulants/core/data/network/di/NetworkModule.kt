package biz.belcorp.salesforce.modules.postulants.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_GOOGLE_MAPS_API
import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.core.data.network.di.NAMED_SOMOS_BELCORP
import biz.belcorp.salesforce.modules.postulants.core.data.network.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideGoogleApi(get(named(NAMED_GOOGLE_MAPS_API))) }
    single { provideUneteApi(get(named(NAMED_MOBILE))) }
    single { provideZonaContraseniaApi(get(named(NAMED_MOBILE))) }
    single { provideUndicadorUnete(get(named(NAMED_MOBILE))) }
    single { provideSyncPostulantesApi(get(named(NAMED_MOBILE))) }
    single { provideValidacionCrediticiaRestApi(get(named(NAMED_SOMOS_BELCORP))) }
    single { provideCoordenadasApi(get(named(NAMED_SOMOS_BELCORP))) }
}

private fun provideGoogleApi(retrofit: Retrofit): GoogleApi {
    return retrofit.create(GoogleApi::class.java)
}

private fun provideUneteApi(retrofit: Retrofit): UneteApi {
    return retrofit.create(UneteApi::class.java)
}

private fun provideSyncPostulantesApi(retrofit: Retrofit): SyncPostulantesApi {
    return retrofit.create(SyncPostulantesApi::class.java)
}

private fun provideValidacionCrediticiaRestApi(retrofit: Retrofit): ValidacionCrediticiaRestApi {
    return retrofit.create(ValidacionCrediticiaRestApi::class.java)
}

private fun provideZonaContraseniaApi(retrofit: Retrofit): ZonaContraseniaApi {
    return retrofit.create(ZonaContraseniaApi::class.java)
}

private fun provideUndicadorUnete(retrofit: Retrofit): IndicadorApi {
    return retrofit.create(IndicadorApi::class.java)
}

private fun provideCoordenadasApi(retrofit: Retrofit): CoordendasApi {
    return retrofit.create(CoordendasApi::class.java)
}


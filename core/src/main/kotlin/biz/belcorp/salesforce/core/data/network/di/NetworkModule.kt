package biz.belcorp.salesforce.core.data.network.di

import android.content.Context
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.network.SomosBelcorpApi
import biz.belcorp.salesforce.core.data.network.SyncApi
import biz.belcorp.salesforce.core.data.network.WsSomosBelcorpApi
import biz.belcorp.salesforce.core.data.network.utils.*
import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.utils.isOnline
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val NAMED_MOBILE = "MOBILE"
const val NAMED_MOBILE_NEW = "MOBILE_NEW"
const val NAMED_MOBILE_CLIENT = "MOBILE_CLIENT"
const val NAMED_BELCORP = "BELCORP"
const val NAMED_BELCORP_CLIENT = "BELCORP_CLIENT"
const val NAMED_WS_SOMOS_BELCORP = "WS_SOMOS_BELCORP"
const val NAMED_WS_SOMOS_BELCORP_CLIENT = "WS_SOMOS_BELCORP_CLIENT"
const val NAMED_SOMOS_BELCORP = "SOMOS_BELCORP"
const val NAMED_SOMOS_BELCORP_NEW = "SOMOS_BELCORP_NEW"
const val NAMED_SOMOS_BELCORP_CLIENT = "SOMOS_BELCORP_CLIENT"
const val NAMED_GOOGLE_MAPS_API = "GOOGLE_MAPS_API"

internal val networkModule = module {

    single { createCache(get()) }

    single(named(NAMED_MOBILE_CLIENT)) { createMobileHttpClient(get(), get()) }
    single(named(NAMED_BELCORP_CLIENT)) { createBelcorpHttpClient(get(), get(), get(), get()) }
    single(named(NAMED_WS_SOMOS_BELCORP_CLIENT)) { createSomosBelcorpHttpClient(get(), get()) }
    single(named(NAMED_SOMOS_BELCORP_CLIENT)) { createOkHttpClient(get(), get()) }

    single(named(NAMED_MOBILE)) { retrofitMobile(get(named(NAMED_MOBILE_CLIENT))) }
    single(named(NAMED_MOBILE_NEW)) { retrofitMobileNew(get(named(NAMED_MOBILE_CLIENT))) }
    single(named(NAMED_BELCORP)) { retrofitBelcorp(get(named(NAMED_BELCORP_CLIENT))) }
    single(named(NAMED_WS_SOMOS_BELCORP)) {
        retrofitWsSomosBelcorpApi(
            get(
                named(
                    NAMED_WS_SOMOS_BELCORP_CLIENT
                )
            )
        )
    }
    //single(named(NAMED_SOMOS_BELCORP)) { retrofitSomosBelcorpApi(get(named(NAMED_SOMOS_BELCORP_CLIENT)))}
    single(named(NAMED_SOMOS_BELCORP)) { retrofitSomosBelcorpApi(get(), get()) }
    single(named(NAMED_SOMOS_BELCORP_NEW)) { retrofitSomosBelcorpApiNew(get(), get()) }
    single(named(NAMED_GOOGLE_MAPS_API)) { retrofitGoogleMapsApi() }

    single { provideSyncApi(get(named(NAMED_MOBILE))) }
    single { provideBelcorpApi(get(named(NAMED_BELCORP))) }
    single { provideWsSomosBelcorpApi(get(named(NAMED_WS_SOMOS_BELCORP))) }
    single { provideSomosBelcorpApi(get(named(NAMED_SOMOS_BELCORP_NEW))) }
}

/**
 * Mobile Api
 * */

private fun createMobileHttpClient(preferences: AuthSharedPreferences, appBuild: AppBuild): OkHttpClient {
    return createOkHttpClient {
        addAuthorizationHeader(preferences.legacyToken)
        addVersionName(appBuild.versionName)
    }
}

private fun retrofitMobile(okHttpClient: OkHttpClient): Retrofit {
    return createRetrofit(okHttpClient, BuildConfig.HOST)
}

private fun retrofitMobileNew(okHttpClient: OkHttpClient): Retrofit {
    return createRetrofitNew(okHttpClient, BuildConfig.HOST)
}

private fun provideSyncApi(retrofit: Retrofit): SyncApi {
    return retrofit.create(SyncApi::class.java)
}

/**
 * Belcorp Api
 * */

private fun createBelcorpHttpClient(
    context: Context,
    configPref: ConfigSharedPreferences,
    authPref: AuthSharedPreferences,
    appBuild: AppBuild
): OkHttpClient {
    return createOkHttpClient {
        addAccessTokenHeader(authPref.token)
        addCacheHeader(context.isOnline(), configPref)
        addVersionName(appBuild.versionName)
    }
}

private fun retrofitBelcorp(okHttpClient: OkHttpClient): Retrofit {
    return createRetrofitNew(okHttpClient, BuildConfig.BELCORP_HOST)
}

private fun provideBelcorpApi(retrofit: Retrofit): BelcorpApi {
    return retrofit.create(BelcorpApi::class.java)
}


/**
 * Somos Belcorp Api
 * */

private fun createSomosBelcorpHttpClient(preferences: AuthSharedPreferences, appBuild: AppBuild): OkHttpClient {
    return createOkHttpClient {
        addPlainTextHeader()
        addAuthorizationHeader(preferences.sbToken)
        addVersionName(appBuild.versionName)
    }
}

private fun retrofitWsSomosBelcorpApi(okHttpClient: OkHttpClient): Retrofit {
    return createRetrofitNew(okHttpClient, BuildConfig.SOMOS_BELCORP_HOST)
}

private fun retrofitSomosBelcorpApi(preferences: AuthSharedPreferences, appBuild: AppBuild): Retrofit {
    return createRetrofit(createOkHttpClient {
        addPlainTextHeader()
        addAuthorizationHeader(preferences.deviceSBToken)
        addVersionName(appBuild.versionName)
    }, BuildConfig.API_SB_HOST)
}

private fun retrofitSomosBelcorpApiNew(preferences: AuthSharedPreferences, appBuild: AppBuild): Retrofit {
    return createRetrofitNew(createOkHttpClient {
        addPlainTextHeader()
        addAuthorizationHeader(preferences.deviceSBToken)
        addVersionName(appBuild.versionName)
    }, BuildConfig.API_SB_HOST)
}

private fun provideWsSomosBelcorpApi(retrofit: Retrofit): WsSomosBelcorpApi {
    return retrofit.create(WsSomosBelcorpApi::class.java)
}

private fun provideSomosBelcorpApi(retrofit: Retrofit): SomosBelcorpApi {
    return retrofit.create(SomosBelcorpApi::class.java)
}

/**
 * External Api
 * */
private fun retrofitGoogleMapsApi(): Retrofit {
    return createRetrofit(BuildConfig.API_GOOGLE_MAPS)
}


package biz.belcorp.salesforce.core.data.preferences.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import biz.belcorp.salesforce.core.data.preferences.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {

    single {
        val masterKey = MasterKey.Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encPrefs = EncryptedSharedPreferences.create(
            androidContext(),
            UserSharedPreferences.PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        UserSharedPreferences(encPrefs)
    }

    single {
        val masterKey = MasterKey.Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encPrefs = EncryptedSharedPreferences.create(
            androidContext(),
            AuthSharedPreferences.PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        AuthSharedPreferences(encPrefs)
    }

    single {
        val masterKey = MasterKey.Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encPrefs = EncryptedSharedPreferences.create(
            androidContext(),
            SyncSharedPreferences.PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        SyncSharedPreferences(encPrefs)
    }

    single {
        val masterKey = MasterKey.Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encPrefs = EncryptedSharedPreferences.create(
            androidContext(),
            ConfigSharedPreferences.PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        ConfigSharedPreferences(encPrefs)
    }

    single {
        val masterKey = MasterKey.Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encPrefs = EncryptedSharedPreferences.create(
            androidContext(),
            FeaturesSharedPreferences.PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        FeaturesSharedPreferences(encPrefs)
    }

    factory { WebViewCookieManager() }
}

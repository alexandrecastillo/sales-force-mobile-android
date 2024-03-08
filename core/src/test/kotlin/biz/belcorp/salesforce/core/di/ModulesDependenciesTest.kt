package biz.belcorp.salesforce.core.di

import android.content.Context
import biz.belcorp.salesforce.core.data.network.SomosBelcorpApi
import biz.belcorp.salesforce.core.data.preferences.*
import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.utils.listByElementsOf
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun injectDependenciesTestModules() {
    startKoin {
        printLogger()
        modules(baseDependenciesTestModules)
    }
}

private val baseDependenciesTestModules by lazy {
    listByElementsOf<Module>(
        coreModules,
        baseDependenciesMocksModule
    )
}

private val baseDependenciesMocksModule = module {

    factory(override = true) { mockk<Context>() }

    factory(override = true) { mockk<PostExecutionThread>() }
    factory(override = true) { mockk<ThreadExecutor>() }

    single(override = true) { mockk<UserSharedPreferences>() }
    single(override = true) { mockk<AuthSharedPreferences>() }
    single(override = true) { mockk<SyncSharedPreferences>() }
    single(override = true) { mockk<ConfigSharedPreferences>() }
    single(override = true) { mockk<FeaturesSharedPreferences>() }
    single(override = true) { mockk<FirebaseMessaging>() }
    single(override = true) { mockk<FirebaseRemoteConfig>() }

    single(override = true) { mockk<AuthTokenRenewer>() }
    single(override = true) { mockk<SomosBelcorpApi>() }

    single(named(DEVICE_UUID), override = true) { "UUID" }

}

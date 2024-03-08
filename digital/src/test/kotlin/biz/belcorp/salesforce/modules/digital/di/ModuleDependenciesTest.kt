package biz.belcorp.salesforce.modules.digital.di

import android.content.Context
import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.core.di.coreModules
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.utils.listByElementsOf
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.module.Module
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
        digitalFeatureModules,
        baseDependenciesMocksModule
    )
}

private val baseDependenciesMocksModule = module {

    factory(override = true) { mockk<Context>() }
    factory(override = true) { mockk<PostExecutionThread>() }
    factory(override = true) { mockk<ThreadExecutor>() }

    single(override = true) { mockk<ConfigSharedPreferences>() }
    single(override = true) { mockk<UserSharedPreferences>() }
    single(override = true) { mockk<AuthSharedPreferences>() }
    single(override = true) { mockk<SyncSharedPreferences>() }

    single(override = true) { mockk<AuthTokenRenewer>() }

}

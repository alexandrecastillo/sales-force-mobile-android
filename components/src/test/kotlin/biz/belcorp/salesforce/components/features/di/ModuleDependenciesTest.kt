package biz.belcorp.salesforce.components.features.di

import android.content.Context
import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.di.coreModules
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
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
        baseDependenciesMocksModule
    )
}
private val baseDependenciesMocksModule = module {

    factory(override = true) { mockk<Context>() }

    single(override = true) { mockk<ConfigSharedPreferences>() }
    single(override = true) { mockk<AuthSharedPreferences>() }
    single(override = true) { mockk<SyncSharedPreferences>() }

    factory(override = true) { mockk<SessionRepository>() }

}

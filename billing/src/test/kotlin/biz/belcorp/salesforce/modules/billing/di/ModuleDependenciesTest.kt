package biz.belcorp.salesforce.modules.billing.di

import android.content.Context
import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.core.di.coreModules
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.billing.core.data.network.RejectedOrdersApi
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
        billingFeatureModules,
        baseDependenciesMocksModule
    )
}

private val baseDependenciesMocksModule = module {

    factory(override = true) { mockk<Context>() }
    factory(override = true) { mockk<PostExecutionThread>() }
    factory(override = true) { mockk<ThreadExecutor>() }

    factory(override = true) { mockk<UaInfoUseCase>() }
    factory(override = true) { mockk<UaInfoMapper>() }

    factory(override = true) { mockk<ObtenerSesionUseCase>() }
    factory(override = true) { mockk<SessionRepository>() }
    factory(override = true) { mockk<CampaniasRepository>() }
    factory(override = true) { mockk<ConfigurationRepository>() }

    single(override = true) { mockk<ConfigSharedPreferences>() }
    single(override = true) { mockk<UserSharedPreferences>() }
    single(override = true) { mockk<AuthSharedPreferences>() }
    single(override = true) { mockk<SyncSharedPreferences>() }

    factory(override = true) { mockk<BelcorpApi>() }
    single(override = true) { mockk<RejectedOrdersApi>() }

    single(override = true) { mockk<AuthTokenRenewer>() }

}

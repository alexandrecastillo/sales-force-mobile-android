package biz.belcorp.salesforce.modules.consultants.di

import android.content.Context
import biz.belcorp.salesforce.core.data.preferences.*
import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.core.di.coreModules
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultantsApi
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultorasApi
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun injectDependenciesTestModules() {
    startKoin {
        printLogger()
        modules(dynamicFeatureDependenciesTestModules)
    }
}

private val dynamicFeatureDependenciesTestModules by lazy {
    listByElementsOf<Module>(
        coreModules,
        dynamicFeatureModules,
        dynamicFeatureDependenciesMocksModule
    )
}

private val dynamicFeatureDependenciesMocksModule = module {

    factory(override = true) { mockk<Context>() }

    factory(override = true) { mockk<PostExecutionThread>() }
    factory(override = true) { mockk<ThreadExecutor>() }

    single(override = true) { mockk<UserSharedPreferences>() }
    single(override = true) { mockk<AuthSharedPreferences>() }
    single(override = true) { mockk<SyncSharedPreferences>() }
    single(override = true) { mockk<ConfigSharedPreferences>() }
    single(override = true) { mockk<FeaturesSharedPreferences>() }

    factory(override = true) { mockk<SessionRepository>() }
    factory(override = true) { mockk<CampaniasRepository>() }
    factory(override = true) { mockk<ObtenerSesionUseCase>() }
    factory(override = true) { mockk<ObtenerCampaniasUseCase>() }

    single(override = true) { mockk<ConsultorasApi>() }
    single(override = true) { mockk<ConsultantsApi>() }

    single(override = true) { mockk<AuthTokenRenewer>() }

}

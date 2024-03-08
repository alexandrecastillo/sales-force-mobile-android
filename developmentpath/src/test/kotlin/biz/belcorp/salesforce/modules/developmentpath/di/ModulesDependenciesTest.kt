package biz.belcorp.salesforce.modules.developmentpath.di

import android.content.Context
import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.preferences.*
import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataMapper
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataStore
import biz.belcorp.salesforce.core.data.repository.logs.EscribirArchivoLocalDataStore
import biz.belcorp.salesforce.core.di.coreModules
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfo
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfoRetriever
import biz.belcorp.salesforce.core.domain.entities.hardware.NetworkStatus
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.kinesis.KinesisManagerCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.ObtenerListadoCampanaFinUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.ObtenerSesionPersonaUseCase
import com.google.gson.Gson
import io.mockk.every
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
        developmentPathModules,
        developmentPathFeatureDependenciesMocksModule
    )
}

private val developmentPathFeatureDependenciesMocksModule = module(override = true) {

    factory { mockk<Context>() }
    factory { mockk<PostExecutionThread>() }
    factory { mockk<ThreadExecutor>() }

    single(override = true) { mockk<UserSharedPreferences>() }
    single(override = true) { mockk<AuthSharedPreferences>() }
    single(override = true) { mockk<SyncSharedPreferences>() }
    single(override = true) { mockk<FeaturesSharedPreferences>() }
    single(override = true) { mockk<ConfigSharedPreferences>() }

    factory { mockk<SessionRepository>() }
    factory { mockk<CampaniasRepository>() }
    factory { mockk<ConsultantsSyncRepository>() }
    factory { mockk<ObtenerSesionPersonaUseCase>() }
    factory { mockk<ObtenerCampaniasUseCase>() }
    factory { mockk<ObtenerListadoCampanaFinUseCase>() }
    factory {
        val hardwareMockk = mockk<HardwareInfoRetriever>()
        val hardwareInfo = HardwareInfo(BuildVariant.DEVELOP, NetworkStatus.CONNECTED)
        every { hardwareMockk.get() } returns hardwareInfo
        hardwareMockk
    }
    factory { mockk<HardwareInfo>() }

    factory { mockk<EscribirArchivoLocalDataStore>() }

    factory { mockk<KinesisManagerCloudDataStore>() }
    factory { mockk<CampaniasDataStore>() }
    factory { mockk<CampaniasDataMapper>() }
    factory { mockk<Gson>() }
    single { mockk<SyncRestApi>() }
    single { mockk<GoogleApi>() }
    single { mockk<GraficosApi>() }
    single { mockk<PerfilApi>() }
    single { mockk<TipsOfertasRestApi>() }

    single(override = true) { mockk<AuthTokenRenewer>() }

    factory(override = true) { mockk<BelcorpApi>() }
    factory(override = true) { mockk<SyncProfileInfoApi>() }

}


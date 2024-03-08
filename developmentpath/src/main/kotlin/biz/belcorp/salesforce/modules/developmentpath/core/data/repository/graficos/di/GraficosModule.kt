package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.cloud.GraficosGRCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.GraficosGRLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.GraphicsSEDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.graficos.GraficoGrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.graficos.GraphicsSEMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.GraficosGRDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.GraphicsSEDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.ProfileSeCapitalizationSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.ProfileSeCapitalizationCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.data.ProfileCapitalizationDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.mapper.ProfileCapitalizationSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.ProfileProfitSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.ProfileProfitCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.data.ProfileProfitDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.mapper.ProfileProfitSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.capitalization.ProfileSeCapitalizationSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.profit.ProfileProfitSyncRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val graficosModule by lazy {
    listByElementsOf<Module>(
        graficosGRModule,
        graphicsSEModule
    )
}

private val graficosGRModule = module {
    factory { GraficoGrMapper() }
    factory { GraficosGRCloudDataStore(get(), get()) }
    factory { GraficosGRLocalDataStore() }

    factory<GraficosGrRepository> {
        GraficosGRDataRepository(
            graficosGRLocalDataStore = get(),
            graficosGRCloudDataStore = get(),
            graficoGrMapper = get()
        )
    }
}

private val graphicsSEModule = module {
    factory { GraphicsSEMapper() }
    factory { GraphicsSEDataStore() }
    factory<GraphicsSERepository> { GraphicsSEDataRepository(get(), get()) }

    factory { ProfileSeCapitalizationCloudStore(get(), get()) }
    factory { ProfileCapitalizationDataStore() }
    factory { ProfileCapitalizationSyncMapper() }
    factory<ProfileSeCapitalizationSyncRepository> {
        ProfileSeCapitalizationSyncDataRepository(get(), get(), get())
    }

    factory { ProfileProfitCloudStore(get(), get()) }
    factory { ProfileProfitDataStore() }
    factory { ProfileProfitSyncMapper() }
    factory<ProfileProfitSyncRepository> { ProfileProfitSyncDataRepository(get(), get(), get()) }

}

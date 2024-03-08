package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.di

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository.SyncField.CapitalizationKpi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.CapitalizationDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.PostulantsKpiDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.CapitalizationCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.PostulantsKpiCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data.CapitalizationDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data.PostulantsKpiDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.PostulantsKpiMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.CapitalizationMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.PostulantsKpiRepository
import org.koin.dsl.module

internal val capitalizationModules = module {

    factory { PostulantsKpiCloudStore(get(), get()) }
    factory { CapitalizationCloudStore(get(), get()) }
    factory { PostulantsKpiDataStore() }
    factory { CapitalizationDataStore() }
    factory { PostulantsKpiMapper() }
    factory { CapitalizationMapper() }
    factory<PostulantsKpiRepository> {
        PostulantsKpiDataRepository(get(), get(), get(), get(), CapitalizationKpi)
    }
    factory<CapitalizationRepository> {
        CapitalizationDataRepository(get(), get(), get(), get())
    }
}

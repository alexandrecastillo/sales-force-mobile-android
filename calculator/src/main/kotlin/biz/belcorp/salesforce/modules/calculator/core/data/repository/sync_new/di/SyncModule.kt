package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.di

import biz.belcorp.salesforce.modules.calculator.core.data.datasource.ResultsLastCampaignDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.mappers.ResultsLastCampaignMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.ResultsLastCampaignDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.CampaignProjectionRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.CampaignProjectionCloudDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.data.CampaignProjectionDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.mapper.CampaignProjectionMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable.ResultsLastCampaignRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.sync.SyncRepository
import org.koin.dsl.module

val syncModule = module {

    factory { CampaignProjectionCloudDataStore(get(), get()) }
    factory { CampaignProjectionDataStore() }
    factory { CampaignProjectionMapper() }
    factory { ResultsLastCampaignDataStore() }
    factory { ResultsLastCampaignMapper() }

    factory<ResultsLastCampaignRepository> { ResultsLastCampaignDataRepository(get(), get()) }

    factory<SyncRepository> { CampaignProjectionRepository(get(), get(), get()) }

}

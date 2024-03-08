package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.di

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository.SyncField.Consultants
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.ConsultantsDataSyncRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.ConsultantsCloudStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsTableDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsMapper
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsTableMapper
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import org.koin.dsl.module

internal val consultantsModule = module {

    factory { ConsultantsCloudStore(get(), get()) }
    factory { ConsultantsTableDataStore() }
    factory { ConsultantsDataStore() }
    factory { ConsultantsTableMapper() }
    factory { ConsultantsMapper() }

    factory<ConsultantsSyncRepository> {
        ConsultantsDataSyncRepository(get(), get(), get(), get(), get(), get(), Consultants)
    }

}

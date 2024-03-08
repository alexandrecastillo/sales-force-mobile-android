package biz.belcorp.salesforce.core.data.repository.businesspartners.di

import biz.belcorp.salesforce.core.data.repository.businesspartners.BusinessPartnerDataRepository
import biz.belcorp.salesforce.core.data.repository.businesspartners.BusinessPartnerSyncDataRepository
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.BusinessPartnerCloudStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerTableDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerMapper
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerTableMapper
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerSyncRepository
import org.koin.dsl.module

internal val businessPartnerModule = module {

    factory { BusinessPartnerCloudStore(get(), get()) }

    factory { BusinessPartnerDataStore() }
    factory { BusinessPartnerTableDataStore() }

    factory { BusinessPartnerMapper() }
    factory { BusinessPartnerTableMapper() }

    factory<BusinessPartnerRepository> { BusinessPartnerDataRepository(get(), get()) }
    factory<BusinessPartnerSyncRepository> {
        BusinessPartnerSyncDataRepository(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

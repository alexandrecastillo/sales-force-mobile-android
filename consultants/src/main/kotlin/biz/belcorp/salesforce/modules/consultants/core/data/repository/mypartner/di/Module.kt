package biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.di

import biz.belcorp.salesforce.modules.consultants.core.data.datasource.MyPartnerMapper
import biz.belcorp.salesforce.modules.consultants.core.data.datasource.MyPartnersDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.MyPartnerRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.cloud.MyPartnerCloudStore
import org.koin.dsl.module

val myPartnerModule = module {
    factory { MyPartnerMapper() }
    factory { MyPartnersDataStore() }
    factory { MyPartnerCloudStore(get(), get()) }
    factory { MyPartnerRepository(get(), get(), get()) }
}

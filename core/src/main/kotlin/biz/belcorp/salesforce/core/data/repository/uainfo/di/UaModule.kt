package biz.belcorp.salesforce.core.data.repository.uainfo.di

import biz.belcorp.salesforce.core.data.repository.uainfo.UaInfoDataRepository
import biz.belcorp.salesforce.core.data.repository.uainfo.cloud.UaInfoCloudStore
import biz.belcorp.salesforce.core.data.repository.uainfo.data.UaInfoDataStore
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.RegionTableMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.SeccionTableMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.UaInfoMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.ZonaTableMapper
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import org.koin.dsl.module

val uaInfoModule = module {

    factory { UaInfoMapper() }
    factory { SeccionTableMapper() }
    factory { ZonaTableMapper() }
    factory { RegionTableMapper() }
    factory { UaInfoCloudStore(get(), get()) }
    factory { UaInfoDataStore() }

    factory<UaInfoRepository> {
        UaInfoDataRepository(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

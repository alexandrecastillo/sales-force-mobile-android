package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.di

import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.GroupSegCloudStore
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.GroupSegDataRepository
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.GroupSegDataStore
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.SegmentationDataStore
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.mappers.GroupSegDataMapper
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository
import org.koin.dsl.module

val groupSegModule = module {

    factory { SegmentationDataStore() }
    factory { GroupSegDataStore() }
    factory { GroupSegCloudStore(get(), get()) }
    factory { GroupSegDataMapper() }

    factory<GroupSegRepository> { GroupSegDataRepository(get(), get(), get(), get()) }
}

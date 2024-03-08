package biz.belcorp.salesforce.core.data.repository.directory.di

import biz.belcorp.salesforce.core.data.repository.directory.ManagerDirectoryDataRepository
import biz.belcorp.salesforce.core.data.repository.directory.ManagerDirectorySyncDataRepository
import biz.belcorp.salesforce.core.data.repository.directory.cloud.ManagerDirectoryCloudStore
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryDataStore
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryTableDataStore
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryMapper
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryTableMapper
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectorySyncRepository
import org.koin.dsl.module

internal val managerDirectoryModule = module {
    factory { ManagerDirectoryCloudStore(get(), get()) }
    factory { ManagerDirectoryDataStore() }
    factory { ManagerDirectoryMapper() }
    factory<ManagerDirectorySyncRepository> {
        ManagerDirectorySyncDataRepository(get(), get(), get(), get(), get())
    }
    factory<ManagerDirectoryRepository> { ManagerDirectoryDataRepository(get(), get()) }
    factory { ManagerDirectoryTableDataStore() }
    factory { ManagerDirectoryTableMapper() }
}

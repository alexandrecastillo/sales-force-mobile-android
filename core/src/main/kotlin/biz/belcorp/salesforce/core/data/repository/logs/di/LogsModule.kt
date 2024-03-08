package biz.belcorp.salesforce.core.data.repository.logs.di

import biz.belcorp.salesforce.core.data.repository.logs.EscribirArchivoLocalDataStore
import biz.belcorp.salesforce.core.data.repository.logs.LogLocalDataRepository
import biz.belcorp.salesforce.core.domain.repository.logs.LogRepository
import org.koin.dsl.module

internal val logsModule = module {
    factory<LogRepository> { LogLocalDataRepository(get()) }
    factory { EscribirArchivoLocalDataStore(get()) }
}

package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.hitos.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.hito.HitoDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.hito.HitoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.hitos.HitoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.hito.HitoRepository
import org.koin.dsl.module

internal val hitoModule = module {
    factory { HitoMapper() }
    factory { HitoDBDataStore(hitoMapper = get()) }
    factory<HitoRepository> { HitoDataRepository(dbStore = get()) }
}

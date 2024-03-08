package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.desempenio.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.desempenio.DesempenioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.desempenio.DesempenioMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.desempenio.DesempenioDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.desempenio.DesempenioRepository
import org.koin.dsl.module

internal val desempeniosModule = module {
    factory { DesempenioMapper() }
    factory { DesempenioDBDataStore(mapper = get()) }
    factory<DesempenioRepository> { DesempenioDataRepository(dbStore = get()) }
}

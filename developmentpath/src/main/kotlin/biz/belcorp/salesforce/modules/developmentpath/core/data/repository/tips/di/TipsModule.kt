package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tips.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tips.TipsVisitaDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tips.TipsVisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tips.TipsVisitaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tips.TipsVisitaRepository
import org.koin.dsl.module

internal val tipsModule = module {
    factory { TipsVisitaMapper() }
    factory { TipsVisitaDbDataStore(tipsVisitaMapper = get()) }
    factory<TipsVisitaRepository> { TipsVisitaDataRepository(dbStore = get()) }
}

package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.resultados.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.resultados.ResultsLastCampaignDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.resultadovisita.ResultadoVisitasDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.resultados.ResultsLastCampaignMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.resultados.ResultsLastCampaignDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.resultados.ResultadoVisitasDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultadoVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultsLastCampaignRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val resultadosModule by lazy {
    listByElementsOf<Module>(
        campaignResultsModule,
        resultadosVisitaModule
    )
}

private val campaignResultsModule = module {
    factory { ResultsLastCampaignMapper() }
    factory { ResultsLastCampaignDataStore() }
    factory<ResultsLastCampaignRepository> { ResultsLastCampaignDataRepository(get(), get()) }
}

private val resultadosVisitaModule = module {
    factory { ResultadoVisitasDbStore(consultoraRDDMapper = get()) }
    factory<ResultadoVisitasRepository> {
        ResultadoVisitasDataRepository(resultadoVisitasDbStore = get(), syncRestApi = get())
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniaACampaniaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniasRddUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.GetResultsLastCampaignUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.RecuperarFechasCampaniaActual
import org.koin.dsl.module

internal val campaniaModule = module {
    factory { ObtenerCampaniaACampaniaUseCase(get(), get(), get(), get(), get(), get()) }
    factory { GetResultsLastCampaignUseCase(get(), get(), get()) }
    factory { RecuperarFechasCampaniaActual(get(), get(), get(), get()) }
    factory { ObtenerCampaniasRddUseCase(get(), get(), get(), get(), get()) }
}

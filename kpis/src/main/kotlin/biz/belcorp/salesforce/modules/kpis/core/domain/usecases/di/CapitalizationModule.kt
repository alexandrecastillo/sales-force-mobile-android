package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.KpiGridSelectorUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.GridConsolidatedUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.PostulantKpiUseCase
import org.koin.dsl.module

val capitalizationModule = module {
    factory { CapitalizationKpiUseCase(get(), get(), get()) }
    factory { GridConsolidatedUseCase(get(), get(), get(), get()) }
    factory { PostulantKpiUseCase(get(), get()) }
    factory {
        KpiGridSelectorUseCase(get(), get())
    }
}

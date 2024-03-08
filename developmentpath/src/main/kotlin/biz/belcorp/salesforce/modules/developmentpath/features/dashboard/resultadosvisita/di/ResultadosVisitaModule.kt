package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.ResultadoVisitasMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.ResultadoVisitasPresenter
import org.koin.dsl.module

internal val resultadoVisitaModule = module {
    factory { params ->
        ResultadoVisitasPresenter(params[0], get(), get())
    }
    factory { ResultadoVisitasMapper() }
}

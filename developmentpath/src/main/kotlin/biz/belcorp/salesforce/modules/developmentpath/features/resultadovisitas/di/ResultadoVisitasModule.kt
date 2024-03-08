package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas.di

import biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas.ListarResultadoVisitasPresenter
import org.koin.dsl.module

internal val resultadoVisitasModule = module {
    factory { params -> ListarResultadoVisitasPresenter(params[0], get()) }
}

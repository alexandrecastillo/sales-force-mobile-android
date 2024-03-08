package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.PlanificarPresenter
import org.koin.dsl.module

internal val planificarModule = module {
    factory { PlanificarPresenter(get()) }
}

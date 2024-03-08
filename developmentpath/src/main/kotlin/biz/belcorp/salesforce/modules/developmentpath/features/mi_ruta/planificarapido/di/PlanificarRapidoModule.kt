package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas.PersonasEnPlanPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.planificar.PlanificarRapidoPresenter
import org.koin.dsl.module

internal val planificarRapidoModule = module {
    factory { PersonasEnPlanPresenter(get()) }
    factory { PlanificarRapidoPresenter(get(), get()) }
}

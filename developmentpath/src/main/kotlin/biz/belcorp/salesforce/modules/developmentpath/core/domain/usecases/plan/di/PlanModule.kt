package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.EliminarDePlanUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.PersonasEnPlanUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.PlanIdUseCase
import org.koin.dsl.module

internal val planModule = module {
    factory { EliminarDePlanUseCase(get(), get(), get(), get(), get(), get()) }
    factory { PersonasEnPlanUseCase(get(), get(), get()) }
    factory { PlanIdUseCase(get(), get(), get()) }
}

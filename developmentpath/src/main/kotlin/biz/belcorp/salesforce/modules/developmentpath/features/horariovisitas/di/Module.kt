package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.di

import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.HorarioVisitaConsultoraModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.HorarioVisitaContract
import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.HorarioVisitaModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.HorarioVisitaPresenter
import org.koin.dsl.module

val horarioVisitaModule = module {
    factory { HorarioVisitaModelMapper() }
    factory { HorarioVisitaConsultoraModelMapper() }
    factory<HorarioVisitaContract.Presenter> { params ->
        HorarioVisitaPresenter(
            view = params[0],
            useCase = get(),
            horarioVisitaModelMapper = get(),
            horarioVisitaConsultoraModelMapper = get()
        )
    }
}

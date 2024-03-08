package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.di

import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more.IngresosExtraOtrosContract
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more.IngresosExtraOtrosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.IngresosExtraContract
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.IngresosExtraPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OtraMarcaModelMapper
import org.koin.dsl.module

val ingresosExtraModule = module {
    factory { OtraMarcaModelMapper() }
    factory<IngresosExtraContract.Presenter> { params ->
        IngresosExtraPresenter(
            view = params[0],
            useCase = get(),
            mapper = get(),
        )
    }
    factory<IngresosExtraOtrosContract.Presenter> { params ->
        IngresosExtraOtrosPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

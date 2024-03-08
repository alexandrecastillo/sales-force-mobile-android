package biz.belcorp.salesforce.modules.developmentpath.features.profile.information.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.information.DatosCobranzaGananciaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.information.DatosPersonaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.information.mapper.DatosPersonaFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val informationModule = module {
    factory { DatosPersonaFactory() }
    viewModel { DatosPersonaViewModel(get(), get()) }
    factory { params ->
        DatosCobranzaGananciaPresenter(params[0], get(), get())
    }
}

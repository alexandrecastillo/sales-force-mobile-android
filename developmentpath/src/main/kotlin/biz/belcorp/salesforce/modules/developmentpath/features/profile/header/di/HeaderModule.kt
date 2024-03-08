package biz.belcorp.salesforce.modules.developmentpath.features.profile.header.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cabeceraPerfilModule = module {
    factory { PerfilCabeceraMapper() }
    viewModel {
        PerfilCabeceraViewModel(
            useCase = get(),
            mapper = get()
        )
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.CabeceraPerfilMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.CabeceraPerfilViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val oldProfileModule = module {

    factory { CabeceraPerfilMapper() }

    viewModel { CabeceraPerfilViewModel(get(), get()) }

}

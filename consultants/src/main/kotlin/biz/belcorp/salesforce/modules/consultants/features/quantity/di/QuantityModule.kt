package biz.belcorp.salesforce.modules.consultants.features.quantity.di

import biz.belcorp.salesforce.modules.consultants.features.quantity.ConsultantsQuantityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val quantityModule = module {

    viewModel { ConsultantsQuantityViewModel(get(), get(), get(), get()) }

}

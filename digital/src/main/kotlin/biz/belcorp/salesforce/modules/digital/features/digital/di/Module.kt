package biz.belcorp.salesforce.modules.digital.features.digital.di

import biz.belcorp.salesforce.modules.digital.features.digital.mappers.DigitalInfoMapper
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewModel
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.DigitalConsolidatedViewModel
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid.DigitalConsolidatedMapper
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid.DigitalGridCreator
import biz.belcorp.salesforce.modules.digital.features.digital.view.header.DigitalHeaderViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val digitalModule = module {

    factory { DigitalInfoMapper(get()) }
    factory { DigitalConsolidatedMapper(get(), get()) }
    factory { DigitalGridCreator() }

    viewModel { DigitalViewModel(get(), get(), get(), get(), get()) }
    viewModel { DigitalHeaderViewModel(get(), get(), get()) }
    viewModel { DigitalConsolidatedViewModel(get(), get()) }

}

package biz.belcorp.salesforce.modules.brightpath.features.brightpath.di

import biz.belcorp.salesforce.modules.brightpath.core.data.datasource.BusinessPartnerChangeLevelDataStore
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.BusinessPartnerChangeLevelMapper
import biz.belcorp.salesforce.modules.brightpath.features.brightpath.BrightPathChangeLevelViewModel
import biz.belcorp.salesforce.modules.brightpath.features.brightpath.BusinessPartnerChangeLevelModelMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val brightPathLevelModule = module {
    factory { BusinessPartnerChangeLevelDataStore() }
    factory { BusinessPartnerChangeLevelMapper() }
    factory { BusinessPartnerChangeLevelModelMapper() }
    viewModel {
        BrightPathChangeLevelViewModel(get(), get(), get(), get(), get(), get(), get(), get())

    }
}

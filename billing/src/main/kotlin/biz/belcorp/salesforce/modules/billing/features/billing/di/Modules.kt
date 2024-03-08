package biz.belcorp.salesforce.modules.billing.features.billing.di

import biz.belcorp.salesforce.modules.billing.features.billing.mapper.BillingMultiProfileHeaderMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingMultiProfileDetailMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingNewCycleMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingPegsMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingRejectedOrdersMapper
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingDetailTextResolver
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingHeaderTextResolver
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.BillingMultiProfileDetailViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile.BillingMultiProfileHeaderViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.se.BillingHeaderViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val billingModule = module {
    viewModel { BillingViewModel(get(), get(), get()) }
    viewModel { BillingMultiProfileHeaderViewModel(get(), get()) }
    viewModel { BillingHeaderViewModel(get(), get()) }
    viewModel { BillingMultiProfileDetailViewModel(get(), get(), get(), get()) }

    factory { BillingHeaderTextResolver(get()) }
    factory { BillingDetailTextResolver(get()) }
    factory { BillingMultiProfileHeaderMapper(get()) }
    factory { BillingNewCycleMapper(get()) }
    factory { BillingPegsMapper(get()) }
    factory { BillingRejectedOrdersMapper() }
    factory { BillingMultiProfileDetailMapper(get(), get()) }
}

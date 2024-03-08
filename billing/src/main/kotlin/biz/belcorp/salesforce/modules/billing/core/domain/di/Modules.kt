package biz.belcorp.salesforce.modules.billing.core.domain.di

import biz.belcorp.salesforce.modules.billing.core.domain.usecases.BillingMultiProfileDetailUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.GetBillingAdvancementUseCase
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.BillingMapper
import org.koin.dsl.module

internal val domainModule = module {

    factory { GetBillingAdvancementUseCase(get(), get(), get(), get()) }
    factory { BillingMultiProfileDetailUseCase(get(), get(), get()) }
    factory { BillingMapper(get()) }

}

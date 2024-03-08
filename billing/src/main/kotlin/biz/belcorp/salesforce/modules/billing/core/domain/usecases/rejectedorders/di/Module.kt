package biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.di

import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersSyncUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersUseCase
import org.koin.dsl.module

val rejectedOrdersUseCase = module {
    factory { RejectedOrdersSyncUseCase(get(), get()) }
    factory { RejectedOrdersUseCase(get(), get()) }
}

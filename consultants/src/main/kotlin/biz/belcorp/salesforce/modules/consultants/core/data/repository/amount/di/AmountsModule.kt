package biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.OrdersAmountDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.OrdersAmountCloudStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.amount.OrdersAmountRepository
import org.koin.dsl.module

val amountsModule = module {

    factory { OrdersAmountCloudStore(get(), get()) }

    factory<OrdersAmountRepository> { OrdersAmountDataRepository(get(), get()) }

}

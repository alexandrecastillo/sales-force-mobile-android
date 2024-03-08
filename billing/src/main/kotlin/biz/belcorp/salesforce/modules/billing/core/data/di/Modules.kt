package biz.belcorp.salesforce.modules.billing.core.data.di

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository.SyncField.RejectedOrders
import biz.belcorp.salesforce.modules.billing.core.data.repository.BillingDataRepository
import biz.belcorp.salesforce.modules.billing.core.data.repository.BillingDataStore
import biz.belcorp.salesforce.modules.billing.core.data.repository.BillingDetailDataRepository
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.BillingDetailDataStore
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.BillingDetailMapper
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.BillingMapper
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.RejectedOrdersMapper
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.RejectedOrdersDataRepository
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.RejectedOrdersCloudStore
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.data.RejectedOrdersDataStore
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingDetailRepository
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingRepository
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository
import org.koin.dsl.module

internal val dataModule = module {

    factory { BillingDataStore() }
    factory { BillingDetailDataStore() }
    factory { BillingMapper() }
    factory { BillingDetailMapper() }

    factory<BillingRepository> { BillingDataRepository(get(), get()) }
    factory<BillingDetailRepository> { BillingDetailDataRepository(get(), get()) }

    factory { RejectedOrdersCloudStore(get(), get()) }
    factory { RejectedOrdersDataStore() }
    factory { RejectedOrdersMapper() }
    factory<RejectedOrdersRepository> {
        RejectedOrdersDataRepository(get(), get(), get(), get(), RejectedOrders)
    }
}

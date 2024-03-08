package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.TransactionAccountDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.TransactionAccountCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.data.TransactionAccountDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.mapper.TransactionAccountMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.estadocuenta.TransactionAccountRepository
import org.koin.dsl.module

internal val transactionAccountModule = module {
    factory { TransactionAccountMapper() }
    factory { TransactionAccountDataStore() }
    factory { TransactionAccountCloudStore(get(), get()) }
    factory<TransactionAccountRepository> {
        TransactionAccountDataRepository(get(), get(), get(), get())
    }
}

package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.di

import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.SaleOrdersDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.SaleOrdersCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.data.SaleOrdersDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.SaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository
import org.koin.dsl.module

internal val saleOrdersModule = module {
    factory { SaleOrdersCloudStore(get(), get()) }
    factory { SaleOrdersDataStore() }
    factory { SaleOrdersMapper() }

    factory<SaleOrdersRepository> { SaleOrdersDataRepository(get(), get(), get(), get()) }
}

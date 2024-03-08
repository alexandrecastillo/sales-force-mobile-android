package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.order.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.order.OrderIndicatorDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.order.OrderIndicatorMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.order.OrderIndicatorRDDDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.order.OrderIndicatorRDDRepository
import org.koin.dsl.module

internal val orderModule = module {
    factory { OrderIndicatorMapper() }

    factory { OrderIndicatorDataStore() }
    factory<OrderIndicatorRDDRepository> {
        OrderIndicatorRDDDataRepository(get(), get())
    }
}

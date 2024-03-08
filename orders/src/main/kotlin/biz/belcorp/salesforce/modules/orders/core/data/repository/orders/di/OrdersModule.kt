package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.di

import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.PedidosWebCloudStore
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.PedidosWebDataRepository
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.PedidosWebDataStore
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.BuscarPedidoResponseDataMapper
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.BuscarPedidosRequestMapper
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.OrderWebEntityDataMapper
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.PedidoResponseDataMapper
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository
import org.koin.dsl.module


val ordersModule = module {

    factory { OrderWebEntityDataMapper() }
    factory { PedidoResponseDataMapper(get()) }
    factory { BuscarPedidosRequestMapper() }
    factory { BuscarPedidoResponseDataMapper() }

    factory { PedidosWebDataStore() }
    factory { PedidosWebCloudStore(get(), get(), get()) }

    factory<PedidosWebRepository> { PedidosWebDataRepository(get(), get(), get(), get()) }

}

package biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.di

import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.BloquearPedidoUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.BuscarPedidosUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.CheckLockConfigUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.DesbloquearPedidoUseCase
import org.koin.dsl.module


val ordersModule = module {

    factory { BloquearPedidoUseCase(get()) }
    factory { BuscarPedidosUseCase(get(), get()) }
    factory { CheckLockConfigUseCase(get()) }
    factory { DesbloquearPedidoUseCase(get()) }

}

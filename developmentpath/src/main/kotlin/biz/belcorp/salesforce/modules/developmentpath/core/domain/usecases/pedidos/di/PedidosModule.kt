package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos.ObtenerCantidadIntencionPedidoUseCase
import org.koin.dsl.module

internal val pedidosModule = module {
    factory { ObtenerCantidadIntencionPedidoUseCase(get(), get(), get(), get(), get()) }
}

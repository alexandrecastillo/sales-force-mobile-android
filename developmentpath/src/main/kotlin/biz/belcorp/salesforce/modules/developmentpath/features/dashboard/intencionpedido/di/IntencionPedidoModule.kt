package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido.IntencionPedidoPresenter
import org.koin.dsl.module

internal val intencionPedidoModule = module {
    factory { params ->
        IntencionPedidoPresenter(params[0], get())
    }
}

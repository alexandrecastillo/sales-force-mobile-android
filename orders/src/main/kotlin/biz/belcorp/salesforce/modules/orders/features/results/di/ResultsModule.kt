package biz.belcorp.salesforce.modules.orders.features.results.di

import biz.belcorp.salesforce.modules.orders.features.results.PedidosWebPresenter
import biz.belcorp.salesforce.modules.orders.features.results.PedidosWebView
import biz.belcorp.salesforce.modules.orders.features.results.mapper.FiltroPedidosWebMapper
import biz.belcorp.salesforce.modules.orders.features.results.mapper.ResultadoItemPedidosWebMapper
import biz.belcorp.salesforce.modules.orders.features.results.mapper.ResultadoPedidosWebMapper
import org.koin.dsl.module

val resultsModule = module {

    factory { ResultadoItemPedidosWebMapper() }
    factory { ResultadoPedidosWebMapper(get()) }
    factory { FiltroPedidosWebMapper() }

    factory { (view: PedidosWebView) ->
        PedidosWebPresenter(view, get(), get(), get(), get(), get(), get(), get(), get())
    }

}

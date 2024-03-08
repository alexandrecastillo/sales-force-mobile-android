package biz.belcorp.salesforce.modules.orders.features.search.di

import biz.belcorp.salesforce.modules.orders.features.search.FiltrosPedidosWebPresenter
import biz.belcorp.salesforce.modules.orders.features.search.FiltrosPedidosWebView
import biz.belcorp.salesforce.modules.orders.features.search.mapper.FiltrosBusquedaMapper
import org.koin.dsl.module

val searchModule = module {

    factory { FiltrosBusquedaMapper() }

    factory { (view: FiltrosPedidosWebView) ->
        FiltrosPedidosWebPresenter(view, get(), get(), get())
    }

}

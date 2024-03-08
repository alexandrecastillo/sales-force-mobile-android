package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.maps.GeolocationPresenter
import org.koin.dsl.module

internal val mapModule = module {
    factory { params -> GeolocationPresenter(params[0], get(), get()) }
}

package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar.ReprogramarPresenter
import org.koin.dsl.module

internal val replanificarModule = module {
    factory { ReprogramarPresenter(get()) }
}

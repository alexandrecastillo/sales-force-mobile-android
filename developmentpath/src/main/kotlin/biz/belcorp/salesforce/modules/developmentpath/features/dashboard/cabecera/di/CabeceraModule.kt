package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraPresenter
import org.koin.dsl.module

internal val cabeceraModule = module {
    factory { CabeceraModelMapper() }
    factory { params ->
        CabeceraPresenter(params[0], get(), get())
    }
}

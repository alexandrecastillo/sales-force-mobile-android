package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionView
import org.koin.dsl.module

val barraNavegacionModule = module {
    factory { BarraNavegacionMapper() }
    factory { (view: BarraNavegacionView) ->
        BarraNavegacionPresenter(view, get(), get())
    }
}

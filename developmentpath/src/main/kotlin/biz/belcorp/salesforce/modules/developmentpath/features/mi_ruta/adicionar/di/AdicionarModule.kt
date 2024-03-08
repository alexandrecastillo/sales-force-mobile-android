package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.AdicionarVisitaPresenter
import org.koin.dsl.module

val adicionarModule = module {
    factory(override = true) { params ->
        AdicionarVisitaPresenter(params[0], get())
    }
}

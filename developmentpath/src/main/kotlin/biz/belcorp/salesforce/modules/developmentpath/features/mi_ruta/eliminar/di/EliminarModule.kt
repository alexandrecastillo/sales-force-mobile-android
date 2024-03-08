package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.EliminarPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.EliminarView
import org.koin.dsl.module

val eliminarModule = module {
    factory { (view: EliminarView) ->
        EliminarPresenter(view, get())
    }
}

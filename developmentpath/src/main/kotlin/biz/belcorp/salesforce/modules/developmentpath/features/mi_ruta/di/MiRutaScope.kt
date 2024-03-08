package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val MI_RUTA_SCOPE = "mi_ruta_scope"
internal const val RDD_PRESENTER = "rdd_presenter"

internal val miRutaModuleScope = module {
    scope(named(MI_RUTA_SCOPE)) {
        scoped(named(RDD_PRESENTER)) {
            RddPresenter(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
    }
}

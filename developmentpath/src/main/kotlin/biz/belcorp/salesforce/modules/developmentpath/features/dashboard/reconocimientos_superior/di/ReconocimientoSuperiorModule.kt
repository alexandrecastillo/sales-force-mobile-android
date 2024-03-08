package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.ReconocimientoASuperiorMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.presenter.ReconocimientosASuperiorPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view.ReconocimientosASuperiorView
import org.koin.dsl.module

internal val reconocimientoSuperiorModule = module {
    factory { (view: ReconocimientosASuperiorView) ->
        ReconocimientosASuperiorPresenter(view, get(), get())
    }
    factory { ReconocimientoASuperiorMapper() }
}

package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.di

import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.model.ReconocimientoDetalleMapper
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.presenter.ReconocimientoASuperiorDetallePresenter
import org.koin.dsl.module

internal val reconocimientosModule = module {
    factory { ReconocimientoDetalleMapper() }
    factory { params -> ReconocimientoASuperiorDetallePresenter(params[0], get(), get()) }
}

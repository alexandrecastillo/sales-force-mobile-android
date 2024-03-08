package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.di

import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.ResumenConsolidadoPresenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.mappers.DetalleIndicatorModelDataMapper
import org.koin.dsl.module

internal val consolidadoResumenModule = module {

    factory { DetalleIndicatorModelDataMapper() }
    factory { ResumenConsolidadoPresenter(get(), get(), get()) }
}

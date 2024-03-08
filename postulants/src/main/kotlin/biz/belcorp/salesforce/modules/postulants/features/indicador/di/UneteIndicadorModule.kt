package biz.belcorp.salesforce.modules.postulants.features.indicador.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.postulants.features.indicador.IndicadorPresenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.di.consolidadoModule
import biz.belcorp.salesforce.modules.postulants.features.indicador.mappers.IndicadorModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.indicador.mappers.SeccionModelDataMapper
import org.koin.core.module.Module
import org.koin.dsl.module


internal val uneteIndicadorModule by lazy {
    listByElementsOf<Module>(
        consolidadoModule,
        indicadorModule
    )
}

internal val indicadorModule = module {
    factory { IndicadorModelDataMapper() }
    factory { SeccionModelDataMapper() }
    factory { IndicadorPresenter(get(), get(), get(), get(), get()) }

}

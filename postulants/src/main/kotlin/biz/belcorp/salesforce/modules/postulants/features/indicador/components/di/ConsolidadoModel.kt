package biz.belcorp.salesforce.modules.postulants.features.indicador.components.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.di.consolidadoListadoModule
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.di.consolidadoResumenModule
import org.koin.core.module.Module

internal val consolidadoModule by lazy {
    listByElementsOf<Module>(
        consolidadoListadoModule,
        consolidadoResumenModule
    )

}

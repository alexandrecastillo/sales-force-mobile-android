package biz.belcorp.salesforce.modules.postulants.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.postulants.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.di.consultoraCrediticiaModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.di.indicadorModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.maps.di.mapsModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete.di.parametroUneteModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.di.postulantModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.di.syncPostulantesModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica.di.tablaLogicaModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta.di.tipoMetaModule
import biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion.di.uneteConfiguracionModule
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        tipoMetaModule,
        consultoraCrediticiaModule,
        postulantModule,
        mapsModule,
        tablaLogicaModule,
        parametroUneteModule,
        uneteConfiguracionModule,
        syncPostulantesModule,
        indicadorModule
    )
}

package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.di.adicionarModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.di.barraNavegacionModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.di.eliminarModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.di.eventosModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.di.planificarModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.di.planificarRapidoModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.di.presenterModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar.di.registrarModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar.di.replanificarModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.di.mapModule
import org.koin.core.module.Module

internal val miRutaModule by lazy {
    listByElementsOf<Module>(
        adicionarModule,
        barraNavegacionModule,
        eventosModule,
        planificarModule,
        planificarRapidoModule,
        miRutaModuleScope,
        presenterModule,
        registrarModule,
        replanificarModule,
        mapModule,
        eliminarModule
    )
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.di.avanceModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.broadcast.SenderClickUaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.di.cabeceraModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.di.focosModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.di.habilidadesModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido.di.intencionPedidoModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.di.reconocimientoSuperiorModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.di.resultadoVisitaModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.di.tabsModule
import org.koin.core.module.Module
import org.koin.dsl.module

internal val extraModules = module {
    factory { SenderClickUaRdd(get()) }
}

internal val dashboardModule by lazy {
    listByElementsOf<Module>(
        extraModules,
        avanceModule,
        cabeceraModule,
        focosModule,
        habilidadesModule,
        intencionPedidoModule,
        reconocimientoSuperiorModule,
        resultadoVisitaModule,
        tabsModule
    )
}

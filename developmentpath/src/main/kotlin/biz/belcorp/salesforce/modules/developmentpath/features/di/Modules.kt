package biz.belcorp.salesforce.modules.developmentpath.features.di

import biz.belcorp.salesforce.core.data.repository.hardware.AndroidHardwareInfoRetriever
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfoRetriever
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.di.analyticsModule
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.di.dashboardModule
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.FlujoCascadaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.FlujoRddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.focos.di.focosModule
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.di.habilidadesModule
import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.di.horarioVisitaModule
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.di.ingresosExtraModule
import biz.belcorp.salesforce.modules.developmentpath.features.manager.KinesisManagerPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.messaging.di.messagingModule
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.miRutaModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.di.graphicsModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.di.profileModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.di.oldProfileModule
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.di.reconocimientosModule
import biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas.di.resultadoVisitasModule
import biz.belcorp.salesforce.modules.developmentpath.features.sync.di.syncModule
import biz.belcorp.salesforce.modules.developmentpath.features.utils.ModuleTextResolver
import org.koin.core.module.Module
import org.koin.dsl.module

internal val extraModule = module {
    factory { KinesisManagerPresenter(get()) }
    factory { FlujoRddPresenter(get()) }
    factory { FlujoCascadaPresenter(get(), get()) }
}

internal val hardwareModule = module {
    factory<HardwareInfoRetriever> { AndroidHardwareInfoRetriever(get()) }
}

internal val utilsModule = module {
    factory { ModuleTextResolver(get()) }
}

internal val featureModules by lazy {
    listByElementsOf<Module>(
        analyticsModule,
        extraModule,
        profileModule,
        oldProfileModule,
        focosModule,
        ingresosExtraModule,
        hardwareModule,
        dashboardModule,
        syncModule,
        miRutaModule,
        habilidadesModule,
        reconocimientosModule,
        resultadoVisitasModule,
        graphicsModule,
        messagingModule,
        utilsModule,
        horarioVisitaModule
    )
}

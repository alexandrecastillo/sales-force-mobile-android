package biz.belcorp.salesforce.modules.developmentpath.core.domain.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias.di.sugerenciasModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.di.acuerdosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics.di.analyticsModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.buscador.di.buscadorPersonasModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera.di.cabeceraModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.di.campaniaModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.di.cobranzaModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.di.comportamientoModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos.di.concursosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.di.dashboardModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.datos.di.datosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo.di.desarrolloModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio.di.desempenioModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.di.dreamModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.estadocuenta.di.estadoCuentaModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.di.eventosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.di.flujoModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.di.focosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.geolocation.di.geolocationModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.di.graphicsModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.di.habilidadesModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.historicos.di.historicosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.di.kinesisModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.di.metasModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.navegacion.di.navegacionModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.observaciones.di.observacionesModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.order.di.orderModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.pedidos.di.pedidosModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.di.planModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.productivas.di.productivasModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.di.profileModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.puntaje.di.puntajeModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ranking.di.rankingModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.di.reconocimientoModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.di.rddModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.di.legacySesionModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.di.sincronizacionModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.di.tipsModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.di.tipsDesarrolloModule
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.di.visitasModule
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules,
        sugerenciasModule
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        kinesisModule,
        analyticsModule,
        acuerdosModule,
        buscadorPersonasModule,
        cabeceraModule,
        campaniaModule,
        cobranzaModule,
        comportamientoModule,
        dashboardModule,
        datosModule,
        desarrolloModule,
        estadoCuentaModule,
        eventosModule,
        flujoModule,
        focosModule,
        graphicsModule,
        habilidadesModule,
        historicosModule,
        metasModule,
        navegacionModule,
        observacionesModule,
        pedidosModule,
        planModule,
        concursosModule,
        desempenioModule,
        profileModule,
        productivasModule,
        puntajeModule,
        rankingModule,
        reconocimientoModule,
        rddModule,
        sincronizacionModule,
        tipsModule,
        tipsDesarrolloModule,
        visitasModule,
        legacySesionModule,
        geolocationModule,
        orderModule,
        dreamModule,
    )
}

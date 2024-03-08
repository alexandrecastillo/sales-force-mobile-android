package biz.belcorp.salesforce.modules.developmentpath.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.acuerdos.di.acuerdosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.campania.di.campaniasModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza.di.cobranzasModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.comportamiento.di.comportamientoModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.concursos.di.concursosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dashboard.di.dashboardModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.desempenio.di.desempeniosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.di.dreamModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.di.dreamBpModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos.di.eventosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos.di.focosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.geolocation.di.geolocationModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.di.graficosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades.di.habilidadesModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.hitos.di.hitoModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators.di.indicatorsModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.kinesis.di.kinesisModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas.di.metasModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.observaciones.di.observacionesModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.order.di.orderModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.perfil.di.datosPerfilModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona.di.personasModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.plan.di.planModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.di.profileModules
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.puntaje.di.puntajeModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.di.rankingModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos.di.reconocimientoModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.resultados.di.resultadosModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta.di.rutaModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync.di.syncModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tips.di.tipsModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.di.tipsDesarrolloModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.di.transactionAccountModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa.di.unidadAdministrativaModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.visitas.di.visitasModule
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        kinesisModule,
        acuerdosModule,
        campaniasModule,
        cobranzasModule,
        comportamientoModule,
        concursosModule,
        dashboardModule,
        desempeniosModule,
        transactionAccountModule,
        eventosModule,
        focosModule,
        geolocationModule,
        graficosModule,
        habilidadesModule,
        hitoModule,
        indicatorsModule,
        metasModule,
        observacionesModule,
        datosPerfilModule,
        personasModule,
        planModule,
        profileModules,
        puntajeModule,
        rankingModule,
        reconocimientoModule,
        resultadosModule,
        rutaModule,
        syncModule,
        tipsModule,
        tipsDesarrolloModule,
        unidadAdministrativaModule,
        visitasModule,
        orderModule,
        dreamModule,
        dreamBpModule,
    )
}

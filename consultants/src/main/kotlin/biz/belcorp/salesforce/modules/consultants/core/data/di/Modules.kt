package biz.belcorp.salesforce.modules.consultants.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.consultants.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.di.amountsModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.di.chatBotModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.di.consultantsModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.di.consultorasModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros.di.filtrosModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.di.geoLocationModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.di.myPartnerModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones.di.seccionesModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.di.syncModule
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.di.unifiedModule
import org.koin.core.module.Module

val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        consultorasModule,
        filtrosModule,
        seccionesModule,
        syncModule,
        consultantsModule,
        amountsModule,
        chatBotModule,
        geoLocationModule,
        unifiedModule,
        myPartnerModule
    )
}

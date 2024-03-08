package biz.belcorp.salesforce.modules.consultants.core.domain.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.amount.di.amountsModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot.di.chatBotModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.di.consultoraModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.di.filtrosModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.location.di.domainMapModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners.di.myPartnersModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.nivel.di.levelModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.di.syncModule
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.di.unifiedModule
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        levelModule,
        consultoraModule,
        filtrosModule,
        syncModule,
        amountsModule,
        chatBotModule,
        domainMapModule,
        unifiedModule,
        myPartnersModule
    )
}

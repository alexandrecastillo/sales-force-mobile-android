package biz.belcorp.salesforce.base.core.domain.di

import biz.belcorp.salesforce.base.core.domain.usecases.options.di.optionsModule
import biz.belcorp.salesforce.base.core.domain.usecases.sync.di.syncModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module


internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        syncModule,
        optionsModule
    )
}

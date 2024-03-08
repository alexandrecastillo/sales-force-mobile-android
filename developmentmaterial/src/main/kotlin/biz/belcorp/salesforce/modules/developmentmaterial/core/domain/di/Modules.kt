package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.di.documentsModule
import org.koin.core.module.Module

val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

val useCasesModules by lazy {
    listByElementsOf<Module>(
        documentsModule
    )
}

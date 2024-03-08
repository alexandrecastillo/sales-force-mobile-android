package biz.belcorp.salesforce.modules.auth.core.domain.di


import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.di.authModule
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        authModule
    )
}

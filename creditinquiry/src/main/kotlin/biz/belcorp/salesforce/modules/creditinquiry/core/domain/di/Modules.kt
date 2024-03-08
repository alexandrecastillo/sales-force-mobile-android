package biz.belcorp.salesforce.modules.creditinquiry.core.domain.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.usecases.di.creditInquiryModule
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        creditInquiryModule
    )
}

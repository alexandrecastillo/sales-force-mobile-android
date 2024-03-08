package biz.belcorp.salesforce.analytics.core.domain.di

import biz.belcorp.salesforce.analytics.core.domain.usecases.di.analyticsModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        analyticsModule
    )
}

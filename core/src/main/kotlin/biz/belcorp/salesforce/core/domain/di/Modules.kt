package biz.belcorp.salesforce.core.domain.di

import biz.belcorp.salesforce.core.domain.usecases.browser.di.webModule
import biz.belcorp.salesforce.core.domain.usecases.campania.di.campaniaModule
import biz.belcorp.salesforce.core.domain.usecases.configuration.di.configurationModule
import biz.belcorp.salesforce.core.domain.usecases.device.di.deviceModule
import biz.belcorp.salesforce.core.domain.usecases.features.di.dynamicFeatureModule
import biz.belcorp.salesforce.core.domain.usecases.firebase.di.firebaseModule
import biz.belcorp.salesforce.core.domain.usecases.searchfilters.di.searchFiltersModule
import biz.belcorp.salesforce.core.domain.usecases.session.di.sessionModule
import biz.belcorp.salesforce.core.domain.usecases.socialbonus.di.socialBonusModule
import biz.belcorp.salesforce.core.domain.usecases.terms.di.termsConditionsModule
import biz.belcorp.salesforce.core.domain.usecases.traceability.di.traceabilityModule
import biz.belcorp.salesforce.core.domain.usecases.ua.di.uaModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        sessionModule,
        campaniaModule,
        searchFiltersModule,
        firebaseModule,
        configurationModule,
        dynamicFeatureModule,
        uaModule,
        webModule,
        deviceModule,
        socialBonusModule,
        traceabilityModule,
        termsConditionsModule
    )
}

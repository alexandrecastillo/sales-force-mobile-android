package biz.belcorp.salesforce.core.data.di

import biz.belcorp.salesforce.core.data.network.di.networkModule
import biz.belcorp.salesforce.core.data.repository.analytics.di.analyticsModule
import biz.belcorp.salesforce.core.data.repository.auth.di.apiHelperModule
import biz.belcorp.salesforce.core.data.repository.browser.di.browserModule
import biz.belcorp.salesforce.core.data.repository.businesspartners.di.businessPartnerModule
import biz.belcorp.salesforce.core.data.repository.campania.di.campaniasModule
import biz.belcorp.salesforce.core.data.repository.configuration.di.configurationModule
import biz.belcorp.salesforce.core.data.repository.consultants.di.consultantsModule
import biz.belcorp.salesforce.core.data.repository.device.di.deviceModule
import biz.belcorp.salesforce.core.data.repository.directory.di.managerDirectoryModule
import biz.belcorp.salesforce.core.data.repository.firebase.di.firebaseModule
import biz.belcorp.salesforce.core.data.repository.logs.di.logsModule
import biz.belcorp.salesforce.core.data.repository.newrelic.di.newRelicModule
import biz.belcorp.salesforce.core.data.repository.searchfilters.di.searchFiltersModule
import biz.belcorp.salesforce.core.data.repository.session.di.sessionModule
import biz.belcorp.salesforce.core.data.repository.socialbonus.di.socialBonusModule
import biz.belcorp.salesforce.core.data.repository.terms.di.termsConditionsModule
import biz.belcorp.salesforce.core.data.repository.uainfo.di.uaInfoModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        sessionModule,
        campaniasModule,
        analyticsModule,
        searchFiltersModule,
        logsModule,
        firebaseModule,
        newRelicModule,
        configurationModule,
        consultantsModule,
        businessPartnerModule,
        managerDirectoryModule,
        uaInfoModule,
        browserModule,
        apiHelperModule,
        deviceModule,
        socialBonusModule,
        termsConditionsModule
    )
}

package biz.belcorp.salesforce.base.features.di


import biz.belcorp.salesforce.base.features.build.di.appBuildModule
import biz.belcorp.salesforce.base.features.deeplinks.di.deepLinksModule
import biz.belcorp.salesforce.base.features.home.di.homeModule
import biz.belcorp.salesforce.base.features.main.di.mainModule
import biz.belcorp.salesforce.base.features.splash.di.splashModule
import biz.belcorp.salesforce.base.features.sync.di.syncModule
import biz.belcorp.salesforce.base.features.webpage.di.webPageModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module


internal val featureModules by lazy {
    listByElementsOf<Module>(
        splashModule,
        mainModule,
        homeModule,
        syncModule,
        webPageModule,
        deepLinksModule,
        appBuildModule
    )
}

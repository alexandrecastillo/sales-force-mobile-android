package biz.belcorp.salesforce.modules.auth.features.di


import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.auth.features.business.di.loginModule
import biz.belcorp.salesforce.modules.auth.features.support.di.supportModule
import org.koin.core.module.Module


internal val featureModules by lazy {
    listByElementsOf<Module>(
        loginModule,
        supportModule
    )
}

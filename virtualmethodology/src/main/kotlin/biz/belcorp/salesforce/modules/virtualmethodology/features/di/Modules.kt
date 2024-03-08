package biz.belcorp.salesforce.modules.virtualmethodology.features.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.di.contactsModule
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.di.methodologyModule
import biz.belcorp.salesforce.modules.virtualmethodology.features.sync.di.syncModule
import org.koin.core.module.Module

val featureModules by lazy {
    listByElementsOf<Module>(
        methodologyModule,
        contactsModule,
        syncModule
    )
}

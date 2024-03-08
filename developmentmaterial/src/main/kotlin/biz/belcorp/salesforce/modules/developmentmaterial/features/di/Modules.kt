package biz.belcorp.salesforce.modules.developmentmaterial.features.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.di.materialsModule
import biz.belcorp.salesforce.modules.developmentmaterial.features.sync.di.syncModule
import org.koin.core.module.Module

internal val featureModules by lazy {
    listByElementsOf<Module>(
        materialsModule,
        syncModule
    )
}

package biz.belcorp.salesforce.components.di


import biz.belcorp.salesforce.components.features.di.sharedFeatureModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import org.koin.core.module.Module

fun injectSharedModule() {
    loadModules(modules)
}

private val modules by lazy {
    listByElementsOf<Module>(sharedFeatureModule)
}

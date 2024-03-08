package biz.belcorp.salesforce.modules.inspires.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.inspires.core.data.di.dataModules
import biz.belcorp.salesforce.modules.inspires.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.inspires.features.di.featureModules
import biz.belcorp.salesforce.modules.inspires.features.base.di.inspiresModuleScope
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(inspiresModule)
    }

}

val inspiresModule by lazy {
    listByElementsOf<Module>(
        domainModules,
        dataModules,
        inspiresModuleScope,
        featureModules
    )
}

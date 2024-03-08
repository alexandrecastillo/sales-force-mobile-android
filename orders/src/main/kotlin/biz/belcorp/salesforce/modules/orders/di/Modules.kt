package biz.belcorp.salesforce.modules.orders.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.orders.core.data.di.dataModules
import biz.belcorp.salesforce.modules.orders.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.orders.features.di.featureModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(dynamicFeatureModules)
    }

}

val dynamicFeatureModules by lazy {
    listByElementsOf<Module>(
        dataModules,
        domainModules,
        featureModules
    )
}

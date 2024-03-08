package biz.belcorp.salesforce.modules.termsconditions.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.termsconditions.features.di.featureModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(dynamicFeatureModules)
    }

}

val dynamicFeatureModules by lazy {
    listByElementsOf<Module>(
        featureModules
    )
}

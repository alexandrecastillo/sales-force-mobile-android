package biz.belcorp.salesforce.modules.developmentmaterial.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.di.dataModules
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.developmentmaterial.features.di.featureModules
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

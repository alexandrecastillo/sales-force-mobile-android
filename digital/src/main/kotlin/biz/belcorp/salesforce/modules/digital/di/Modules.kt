package biz.belcorp.salesforce.modules.digital.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.digital.core.data.di.dataModules
import biz.belcorp.salesforce.modules.digital.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.digital.features.di.featureModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(digitalFeatureModules)
    }

}

val digitalFeatureModules by lazy {
    listByElementsOf<Module>(
        featureModules,
        domainModules,
        dataModules
    )
}

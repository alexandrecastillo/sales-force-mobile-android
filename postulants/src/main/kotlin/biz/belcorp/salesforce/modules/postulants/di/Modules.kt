package biz.belcorp.salesforce.modules.postulants.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.postulants.core.data.di.dataModules
import biz.belcorp.salesforce.modules.postulants.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.postulants.features.di.featureModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(uneteFeatureModules)
    }

}

val uneteFeatureModules by lazy {
    listByElementsOf<Module>(
        dataModules,
        domainModules,
        featureModules
    )
}

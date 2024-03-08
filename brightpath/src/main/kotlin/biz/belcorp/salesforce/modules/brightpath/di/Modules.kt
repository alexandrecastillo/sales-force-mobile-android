package biz.belcorp.salesforce.modules.brightpath.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.brightpath.core.data.di.dataModule
import biz.belcorp.salesforce.modules.brightpath.core.domain.di.domainModule
import biz.belcorp.salesforce.modules.brightpath.features.brightpath.di.brightPathLevelModule
import biz.belcorp.salesforce.modules.brightpath.features.di.featureModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(brightPathModules)
    }

}

private val brightPathModules by lazy {
    listByElementsOf<Module>(
        dataModule,
        domainModule,
        featureModules,
        brightPathLevelModule
    )
}

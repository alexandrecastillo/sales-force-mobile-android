package biz.belcorp.salesforce.modules.developmentpath.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.di.broadcastModule
import biz.belcorp.salesforce.modules.developmentpath.common.exceptions.exceptionModule
import biz.belcorp.salesforce.modules.developmentpath.core.data.di.dataModules
import biz.belcorp.salesforce.modules.developmentpath.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.developmentpath.features.di.featureModules
import biz.belcorp.salesforce.modules.developmentpath.includes.di.includeModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(developmentPathModules)
    }

}

internal val extraModules by lazy {
    listByElementsOf<Module>(
        broadcastModule,
        exceptionModule
    )
}
val developmentPathModules by lazy {
    listByElementsOf<Module>(
        dataModules,
        domainModules,
        extraModules,
        featureModules,
        includeModules
    )
}

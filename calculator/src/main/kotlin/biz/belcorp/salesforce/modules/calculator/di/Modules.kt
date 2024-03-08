package biz.belcorp.salesforce.modules.calculator.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.calculator.core.data.di.dataModules
import biz.belcorp.salesforce.modules.calculator.core.domain.di.domainModules
import biz.belcorp.salesforce.modules.calculator.feature.calculator.base.calculatorModuleScope
import biz.belcorp.salesforce.modules.calculator.feature.di.featureModules
import org.koin.core.module.Module

@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(calculatorModule)
    }

}

val calculatorModule by lazy {
    listByElementsOf<Module>(
        domainModules,
        dataModules,
        calculatorModuleScope,
        featureModules
    )
}

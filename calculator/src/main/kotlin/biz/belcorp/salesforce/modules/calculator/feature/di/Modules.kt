package biz.belcorp.salesforce.modules.calculator.feature.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.calculator.feature.calculator.di.calculatorModule
import biz.belcorp.salesforce.modules.calculator.feature.sync.di.syncModule
import org.koin.core.module.Module

val featureModules by lazy {
    listByElementsOf<Module>(
        calculatorModule,
        syncModule
    )
}

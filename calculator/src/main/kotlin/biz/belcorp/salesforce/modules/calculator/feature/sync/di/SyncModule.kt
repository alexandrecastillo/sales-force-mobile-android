package biz.belcorp.salesforce.modules.calculator.feature.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.calculator.feature.sync.CalculatorSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val syncModule = module {

    factory<SyncManager>(named<CalculatorSyncManager>()) {
        CalculatorSyncManager()
    }

}

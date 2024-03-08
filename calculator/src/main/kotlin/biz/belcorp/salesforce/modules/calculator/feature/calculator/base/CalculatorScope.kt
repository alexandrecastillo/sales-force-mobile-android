package biz.belcorp.salesforce.modules.calculator.feature.calculator.base

import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val CALCULATOR_SCOPE = "calculator_scope"
const val SHARED_NAV = "shared_nav"
const val NAV = "nav"

internal val calculatorModuleScope = module {
    scope(named(CALCULATOR_SCOPE)) {
        scoped(named(NAV)) { NavigationStateLiveData() }
        scoped(named(SHARED_NAV)) {
            NavigationStateObserver(
                get(named(NAV))
            )
        }
    }
}


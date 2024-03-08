package biz.belcorp.salesforce.modules.inspires.features.base.di

import biz.belcorp.salesforce.modules.inspires.features.base.NavigationStateLiveData
import biz.belcorp.salesforce.modules.inspires.features.base.NavigationStateObserver
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val INSPIRES_SCOPE = "inspires_scope"
const val SHARED_NAV = "shared_nav"
const val NAV = "nav"

internal val inspiresModuleScope = module {
    scope(named(INSPIRES_SCOPE)) {
        scoped(named(NAV)) { NavigationStateLiveData() }
        scoped(named(SHARED_NAV)) {
            NavigationStateObserver(
                get(named(NAV))
            )
        }
    }
}


package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsView
import org.koin.dsl.module

internal val tabsModule = module {
    factory { (view: TabsView) ->
        TabsPresenter(view, get())
    }
}

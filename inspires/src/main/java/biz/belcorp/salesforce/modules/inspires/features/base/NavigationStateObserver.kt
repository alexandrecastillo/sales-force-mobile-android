package biz.belcorp.salesforce.modules.inspires.features.base

import biz.belcorp.salesforce.core.events.ConsumableEvent

class NavigationStateObserver(val nav: NavigationStateLiveData) {
    fun postValue(queryFilter: NavigatorResultViewState) {
        nav.postValue(ConsumableEvent(queryFilter))
    }
}


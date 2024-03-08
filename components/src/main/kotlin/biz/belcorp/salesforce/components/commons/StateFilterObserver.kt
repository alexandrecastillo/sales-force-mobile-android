package biz.belcorp.salesforce.components.commons

import biz.belcorp.salesforce.core.events.ConsumableEvent

class StateFilterObserver(val filter: FilterStateLiveData) {
    fun postValue(queryFilter: HashMap<String, Any>) {
        filter.postValue(ConsumableEvent(queryFilter))
    }
}

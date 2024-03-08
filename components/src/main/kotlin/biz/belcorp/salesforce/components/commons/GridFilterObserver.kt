package biz.belcorp.salesforce.components.commons

import biz.belcorp.salesforce.core.events.ConsumableEvent

class GridFilterObserver(val gridFilter: GridFilterLiveData) {
    fun postValue(hashMap: HashMap<*, *>) {
        gridFilter.postValue(ConsumableEvent(hashMap))
    }
}

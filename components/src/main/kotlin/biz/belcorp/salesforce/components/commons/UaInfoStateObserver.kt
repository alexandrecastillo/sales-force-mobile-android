package biz.belcorp.salesforce.components.commons

import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.states.UaInfoState

class UaInfoStateObserver(val header: UaInfoLiveData, val content: UaInfoLiveData) {
    fun postValue(state: UaInfoState) {
        header.postValue(ConsumableEvent(state))
        content.postValue(ConsumableEvent(state))
    }
}

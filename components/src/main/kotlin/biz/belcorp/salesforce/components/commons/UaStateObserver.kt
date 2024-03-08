package biz.belcorp.salesforce.components.commons

import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.states.UaInfoState

class UaStateObserver(val content: UaInfoDataState) {
    fun postValue(state: UaInfoState) {
        content.postValue(ConsumableEvent(state))
    }
}

package biz.belcorp.salesforce.components.commons

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.ConsumableEvent


class UaKeyObserver(val ua: UaKeyLiveData) {
    fun postValue(state: LlaveUA) {
        ua.postValue(ConsumableEvent(state))
    }
}

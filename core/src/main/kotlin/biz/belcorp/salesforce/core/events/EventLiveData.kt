package biz.belcorp.salesforce.core.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class EventLiveData(
    private val source: String,
    private val mSubject: EventSubject
) : LiveData<ConsumableEvent>() {

    fun update(any: ConsumableEvent) {
        postValue(any)
    }

    override fun removeObserver(observer: Observer<in ConsumableEvent>) {
        super.removeObserver(observer)
        if (!hasObservers()) {
            LiveDataBus.unregister(source, mSubject)
        }
    }

}

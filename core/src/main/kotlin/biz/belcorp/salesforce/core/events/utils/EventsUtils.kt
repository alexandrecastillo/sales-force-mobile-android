package biz.belcorp.salesforce.core.events.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus

@Suppress("UNCHECKED_CAST")
inline fun <reified T> consumableObserver(crossinline f: (T) -> Unit) = Observer<ConsumableEvent> {
    it?.runAndConsume {
        it.value.also { value -> if (value is T) f.invoke(value) }
    }
}

inline fun <reified T : Fragment> T.observeEventSubject(
    vararg subjects: EventSubject,
    observer: Observer<ConsumableEvent>
) {
    subjects.forEach {
        LiveDataBus.from<T>(it)
            .observe(viewLifecycleOwner, observer)
    }
}


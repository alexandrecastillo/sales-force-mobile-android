package biz.belcorp.salesforce.core.events


class ConsumableEvent(
    var value: Any? = null,
    private var isConsumed: Boolean = false
) {
    /**
     *  run task & consume event after that
     */
    fun runAndConsume(runnable: () -> Unit) {
        if (!isConsumed) {
            runnable()
            isConsumed = true
        }
    }
}

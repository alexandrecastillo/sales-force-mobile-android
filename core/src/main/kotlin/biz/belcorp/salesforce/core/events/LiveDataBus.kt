package biz.belcorp.salesforce.core.events


object LiveDataBus {

    private const val UID_PATTERN = "%s|%s"
    private const val UID_END_PATTERN = "|%s"

    val subjectMap = HashMap<String, EventLiveData>()

    /**
     * Get the live data or create it if it's not already in memory.
     */
    inline fun <reified T> from(subjectCode: EventSubject): EventLiveData {
        val source = T::class.java.name
        val uid = generateUID(source, subjectCode)
        var liveData = subjectMap[uid]
        if (liveData == null) {
            liveData = EventLiveData(source, subjectCode)
            subjectMap[uid] = liveData
        }
        return liveData
    }

    /**
     * Removes this subject when it has no observers.
     */
    internal fun unregister(source: String, subjectCode: EventSubject) {
        val uid = generateUID(source, subjectCode)
        subjectMap.remove(uid)
    }

    /**
     * Publish an object to the specified subject for all subscribers of that subject.
     */
    fun publish(subject: EventSubject, message: Any) {
        val pattern = String.format(UID_END_PATTERN, subject.name)
        val entries = subjectMap.entries.filter { it.key.endsWith(pattern) }
        entries.forEach { entry ->
            entry.value.update(ConsumableEvent(message))
        }
    }

    fun generateUID(source: String, subjectCode: EventSubject): String {
        return String.format(UID_PATTERN, source, subjectCode.name)
    }

}

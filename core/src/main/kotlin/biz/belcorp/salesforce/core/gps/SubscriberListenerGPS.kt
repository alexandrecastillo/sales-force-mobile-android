package biz.belcorp.salesforce.core.gps

interface SubscriberListenerGPS {
    fun subscribeListener(listener: RequestListenerGPS)
    fun unsubscribeListener()
}

package biz.belcorp.salesforce.core.domain.repository.firebase


interface CloudMessagingRepository {

    fun subscribeTopics(topics: List<String>)

    fun subscribeTopicsWithEnv(topics: List<String>)

    fun unsubscribeTopics(topics: List<String>)

    fun unsubscribeTopicsWithEnv(topics: List<String>)

}
